package com.project.instagramclone.controller.member;

import com.project.instagramclone.jwt.JWTUtil;
import com.project.instagramclone.service.token.ReissueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * refresh 토큰으로 재발급 요청 처리
 * refresh rotate 적용
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name="Reissue service", description="Reissue service API")
public class ReissueController {
    private final ReissueService reissueService;
    private final JWTUtil jwtUtil;
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
        try {
            String refreshToken = extractTokenFromHeader(request);

            // Refresh Token 검증 및 Access Token 재발급
            String newAccessToken = reissueService.reissueAccessToken(jwtUtil.getUsername(refreshToken), refreshToken);

            // 새 Access Token을 응답 헤더에 추가
            response.setHeader("Authorization", "Bearer " + newAccessToken);

            return ResponseEntity.ok("Access token reissued successfully");

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }
    }

    /**
     * HTTP 헤더에서 Bearer Token 추출
     */
    private String extractTokenFromHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new IllegalArgumentException("Refresh token is missing");
    }
}
