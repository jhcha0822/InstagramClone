package com.project.instagramclone.handler;


// form login 성공 이후 JWT 발급
// access token -> jeader
// refresh token -> cookie

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.instagramclone.dto.member.CustomUserDetails;
import com.project.instagramclone.jwt.JWTUtil;
import com.project.instagramclone.service.token.RefreshTokenService;
import com.project.instagramclone.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomFormSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // JWT 생성을 위한 정보 가져오기
        String username = ((CustomUserDetails) authentication.getPrincipal()).getUsername(); // ID
        String nickname = ((CustomUserDetails) authentication.getPrincipal()).getNickname();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        // Access 및 Refresh Token 생성
        String accessToken = jwtUtil.generateToken("access", username, nickname, role);
        String refreshToken = jwtUtil.generateToken("refresh", username, nickname, role);
        response.setHeader("access", accessToken);

        // Redis에 Refresh Token 저장
        refreshTokenService.saveRefreshToken(username, refreshToken);

        // 헤더 노출 설정
        response.setHeader("Access-Control-Expose-Headers", "access");

        // JSON을 Objectmapper을 통해 직렬화하여 전달
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("username", username);
        responseData.put("nickname", nickname);
        responseData.put("role", role);

        new ObjectMapper().writeValue(response.getWriter(), responseData);
    }
}
