package com.project.instagramclone.handler;

import com.project.instagramclone.jwt.JWTUtil;
import com.project.instagramclone.repository.token.RefreshRepository;
import com.project.instagramclone.service.token.RefreshTokenService;
import com.project.instagramclone.util.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;

// 로그아웃 필터: refresh token 만료
@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String requestURI = request.getRequestURI();

        // URI 검증
        if (!requestURI.matches("^\\/logout$")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 메서드 검증
        String requestMethod = request.getMethod();
        if (!requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7); // Bearer 부분 제거
            if(accessToken != null) {
                String username = jwtUtil.getUsername(accessToken);
                refreshTokenService.deleteRefreshToken(username); // Redis에서 refresh token 삭제
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
