package com.project.instagramclone.controller.member;

import com.project.instagramclone.dto.member.ProfileDto;
import com.project.instagramclone.service.member.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Tag(name="Profile service", description="profile service API")
public class ProfileController {

    private final ProfileService profileService;

    // 특정 사용자 프로필 페이지
    @GetMapping("/{nickname}")
    @Operation(summary = "사용자 프로필 조회", description = "특정 사용자 프로필 조회") // 각 API에 대한 설명
    @ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")) //응답 코드에 대한 정보
    @Parameters({
            @Parameter(name = "nickname", description = "닉네임")
    })
    public ResponseEntity<ProfileDto> getProfile(@PathVariable String nickname) {
        ProfileDto profile = profileService.getProfileByNickname(nickname);
        return ResponseEntity.ok(profile);
    }

}
