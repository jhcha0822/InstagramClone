package com.project.instagramclone.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.instagramclone.dto.oauth2.CustomOAuth2User;
import com.project.instagramclone.dto.oauth2.OAuth2UserDto;
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
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * OAuth2 로그인 성공 후 JWT 발급
 * access, refresh -> httpOnly 쿠키
 * 리다이렉트 되기 때문에 헤더로 전달 불가능
 */
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // create JWT
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String username = customOAuth2User.getOauth2Id();
        String nickname = customOAuth2User.getName();
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        Integer expireS = 24 * 60 * 60;
        String access = jwtUtil.createJwt("access", username, nickname, role, 60 * 10 * 1000L);
        String refresh = jwtUtil.createJwt("refresh", username, nickname, role, expireS * 1000L);

        // refresh 토큰 DB 저장
        refreshTokenService.saveRefreshToken(username, expireS, refresh);

        response.addCookie(CookieUtil.createCookie("access", access, 60 * 10));
        response.addCookie(CookieUtil.createCookie("refresh", refresh, expireS));

        // redirect query param 인코딩 후 전달
        // 이후에 JWT 를 읽어서 데이터를 가져올 수도 있지만, JWT 파싱 비용이 많이 들기 때문에
        // 처음 JWT 발급할 때 이름을 함께 넘긴 후, 로컬 스토리지에 저장한다.
        String encodedName = URLEncoder.encode(nickname, "UTF-8");

        response.sendRedirect("http://localhost:3000/oauth2-jwt-header?nickname=" + encodedName);
    }

}