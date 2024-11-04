package com.project.instagramclone.service.token;

import com.project.instagramclone.jwt.JWTUtil;
import com.project.instagramclone.repository.token.RefreshRepository;
import com.project.instagramclone.util.CookieUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReissueService {
//    private final RefreshRepository refreshRepository;
    private final JWTUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public String reissueAccessToken(String username, String refreshToken) {
        String storedRefreshToken = refreshTokenService.getRefreshToken(username);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        // 새로운 access token 생성
        return jwtUtil.generateToken("access", username, jwtUtil.getNickname(refreshToken), jwtUtil.getRole(refreshToken));
    }

//    /**
//     * Refresh Token 재발급 요청 처리
//     * @param username      사용자 ID
//     * @param newRefreshToken 새로 발급된 Refresh Token
//     * @param duration      유효 기간 (밀리초)
//     */
//    public void reissueToken(String username, String newRefreshToken, long duration) {
//        refreshTokenService.saveRefreshToken(username, newRefreshToken, duration);
//    }
//
//    /**
//     * Refresh Token 유효성 검증
//     * @param username      사용자 ID
//     * @param providedToken 요청에서 전달된 Refresh Token
//     * @return 유효한지 여부 (boolean)
//     */
//    public boolean validateRefreshToken(String username, String providedToken) {
//        return Optional.ofNullable(refreshTokenService.getRefreshToken(username))
//                .map(storedToken -> storedToken.equals(providedToken))
//                .orElse(false);
//    }

//    public ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) {
//        String refresh = null;
//        Cookie[] cookies = request.getCookies();
//
//        refresh = Arrays.stream(cookies).filter((cookie) -> cookie.getName().equals("refresh"))
//                .findFirst().get().getValue();
//
//        // 쿠키에 refresh 토큰이 없을 경우
//        if (refresh == null) {
//            return new ResponseEntity<>("refresh token is null", HttpStatus.BAD_REQUEST);
//        }
//
//        // 만료된 토큰은 payload 읽을 수 없음 -> ExpiredJwtException 발생
//        try {
//            jwtUtil.isExpired(refresh);
//        } catch(ExpiredJwtException e){
//            return new ResponseEntity<>("refresh token expired", HttpStatus.BAD_REQUEST);
//        }
//
//        // refresh 토큰이 아닐 경우
//        String category = jwtUtil.getCategory(refresh);
//        if(!category.equals("refresh")) {
//            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
//        }
//
//        String username = jwtUtil.getUsername(refresh);
//        String nickname = jwtUtil.getNickname(refresh);
//        String role = jwtUtil.getRole(refresh);
//
//        // refresh DB 조회
//        Boolean isExist = refreshRepository.existsByRefresh(refresh);
//
//        // DB 에 없는 리프레시 토큰 (혹은 블랙리스트 처리된 리프레시 토큰)
//        if(!isExist) {
//            return new ResponseEntity<>("invalid refresh token", HttpStatus.BAD_REQUEST);
//        }
//
//        // new tokens
//        String newAccess = jwtUtil.createJwt("access", username, nickname, role, 60 * 10 * 1000L);
//        Integer expiredS = 60 * 60 * 24;
//        String newRefresh = jwtUtil.createJwt("refresh", username, nickname, role, expiredS * 1000L);
//
//        // 기존 refresh DB 삭제, 새로운 refresh 저장
//        refreshRepository.deleteByRefresh(refresh);
//        refreshTokenService.saveRefreshToken(username, expiredS, newRefresh);
//
//        response.setHeader("access", newAccess);
//        response.addCookie(CookieUtil.createCookie("refresh", newRefresh, expiredS));
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
