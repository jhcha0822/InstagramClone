package com.project.instagramclone.controller.member;

import com.project.instagramclone.dto.member.JoinDto;
import com.project.instagramclone.service.mail.MailService;
import com.project.instagramclone.service.member.JoinService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name="Join service", description="join service API")
public class JoinController {

    private final JoinService joinService;
    private final MailService mailService;
    private final RedisTemplate<String, String> redisTemplate;

//    @PostMapping("/join")
//    @Operation(summary = "회원가입", description = "회원가입에 사용되는 API ") // 각 API에 대한 설명
//    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")) //응답 코드에 대한 정보
//    @Parameters({
//        @Parameter(name="username", description="유저 ID"), //파라미터에 대한 정보
//        @Parameter(name="password", description="비밀번호"), //파라미터에 대한 정보
//        @Parameter(name="nickname", description="닉네임" ), //파라미터에 대한 정보
//        @Parameter(name="email", description="이메일") //파라미터에 대한 정보
//    })
//    public String joinProc(@ModelAttribute JoinDto joinDto) {
//        joinService.join(joinDto);
//        return "ok";
//    }

    @PostMapping("/join")
    @Operation(summary = "회원가입", description = "회원가입에 사용되는 API ") // 각 API에 대한 설명
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")) // 응답 코드에 대한 정보
    @Parameters({
            @Parameter(name="username", description="유저 ID"),   //파라미터에 대한 정보
            @Parameter(name="password", description="비밀번호"),  //파라미터에 대한 정보
            @Parameter(name="nickname", description="닉네임" ),  //파라미터에 대한 정보
            @Parameter(name="email", description="이메일"),      //파라미터에 대한 정보
            @Parameter(name="code", description="보안 코드")     //파라미터에 대한 정보
    })
    public ResponseEntity<String> join(@RequestBody JoinDto joinDto, @RequestParam(required = false) String code) {
        // Redis에서 보안 코드가 유효한지 확인
        if (code != null && !mailService.verifySecurityCode(joinDto.getEmail(), code)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid security code");
        }

        // 회원 가입 로직 수행
        joinService.join(joinDto);
        redisTemplate.delete(joinDto.getEmail()); // 보안 코드 삭제
        return ResponseEntity.ok("Registration successful");
    }

}
