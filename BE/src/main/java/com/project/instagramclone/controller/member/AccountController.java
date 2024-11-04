package com.project.instagramclone.controller.member;

import com.project.instagramclone.dto.member.AccountUpdateDto;
import com.project.instagramclone.dto.member.PasswordChangeDto;
import com.project.instagramclone.dto.member.CustomUserDetails;
import com.project.instagramclone.dto.member.SearchDto;
import com.project.instagramclone.dto.oauth2.CustomOAuth2User;
import com.project.instagramclone.entity.member.MemberEntity;
import com.project.instagramclone.service.mail.MailService;
import com.project.instagramclone.service.member.AccountService;
import com.project.instagramclone.service.member.MemberService;
import com.project.instagramclone.service.post.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Tag(name="Account service", description="account service API")
public class AccountController {

    private final AccountService accountService;
    private final MemberService memberService;
    private final MailService mailService;
    private final RedisTemplate redisTemplate;

    // 사용자 정보 조회
    @GetMapping("/me")
    @Operation(summary = "내 정보 조회", description = "현재 로그인된 사용자의 정보를 반환합니다.")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json"))
    public ResponseEntity<AccountUpdateDto> getCurrentUser(@AuthenticationPrincipal Object principal) {
        Long memberId = extractMemberId(principal);
        AccountUpdateDto userDto = accountService.getAccount(memberId);  // 서비스에서 유저 정보를 가져옴
        return ResponseEntity.ok(userDto);
    }

    // 사용자 정보 수정
    @PutMapping("/update")
    @Operation(summary = "내 계정 정보 수정", description = "Authorization, DTO 필요") // 각 API에 대한 설명
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")) //응답 코드에 대한 정보
    @Parameters({
            @Parameter(name="nickname", description="닉네임"),
            @Parameter(name="profilePic", description="프로필 이미지 경로"),
            @Parameter(name="bio", description="자기소개")
    })
    public ResponseEntity<?> updateAccount(@ModelAttribute AccountUpdateDto accountUpdateDto,
                                           @RequestParam(value = "profilePicFile", required = false) MultipartFile profilePicFile,
                                           @AuthenticationPrincipal Object principal) {
        Long memberId = extractMemberId(principal);

        // 파일 수신 여부 확인
        if (profilePicFile != null) {
            System.out.println("업로드된 파일 이름: " + profilePicFile.getOriginalFilename());
        }

        try {
            accountService.updateAccount(memberId, accountUpdateDto, profilePicFile);
            return ResponseEntity.ok("계정 정보가 성공적으로 업데이트되었습니다.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 업로드 중 오류가 발생했습니다.");
        }
    }

    // 비밀번호 변경
    @PostMapping("/change-password")
    @Operation(summary = "내 계정의 비밀번호 변경", description = "Authorization, DTO 필요")
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")) //응답 코드에 대한 정보
    @Parameters({
            @Parameter(name="currentPassword", description="현재 비밀번호"),
            @Parameter(name="newPassword", description="새 비밀번호")
    })
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeDto passwordChangeDto, @AuthenticationPrincipal Object principal) {
        Long memberId = extractMemberId(principal);
        boolean isChanged = accountService.changePassword(memberId, passwordChangeDto);
        if (isChanged) {
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("현재 비밀번호가 일치하지 않습니다.");
        }
    }

    // 비밀번호 초기화를 위해 username을 전달
    // username의 이메일로 보안 코드 전송
    @PostMapping("/resetPasswordRequest")
    public ResponseEntity<Map<String, String>> requestPasswordReset(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        response.put("username", accountService.requestPasswordReset(request.get("username")));
        response.put("email", memberService.getMemberByUsername(request.get("username")).getEmail());
        return ResponseEntity.ok(response);
    }

    // 비밀번호 초기화
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String newPassword = request.get("password");
        accountService.resetPassword(username, newPassword);
        redisTemplate.delete(memberService.getMemberByUsername(username).getEmail()); // 보안 코드 삭제
        return ResponseEntity.ok("Password updated successfully");
    }

    // memberId 추출
    private Long extractMemberId(Object principal) {
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getMemberId();
        } else if (principal instanceof CustomOAuth2User) {
            return ((CustomOAuth2User) principal).getMemberId();
        }
        throw new IllegalArgumentException("알 수 없는 사용자 타입");
    }

    // nickname 기반 검색 엔드포인트
    @GetMapping("/search")
    public List<SearchDto> searchMembers(@RequestParam String query) {
        return accountService.searchMembers(query);
    }

}
