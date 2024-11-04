package com.project.instagramclone.service.oauth2;

import com.project.instagramclone.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * OAuth2 리다이렉트 문제로 access 토큰을 httpOnly 쿠키로 발급
 * -> 프론트에서 바로 재요청하면 해당 access 토큰 헤더에 싣고, 쿠키는 만료시킴
 */
@Service
public class OAuth2JwtHeaderService {
    public String oauth2JwtHeaderSet(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        String access = null;

        if(cookies == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "bad";
        }
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("access")){
                access = cookie.getValue();
            }
        }

        if(access == null){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "bad";
        }

        // 클라이언트의 access 토큰 쿠키를 만료
        response.addCookie(CookieUtil.createCookie("access", null, 0));
        response.addHeader("access", access);
        response.setStatus(HttpServletResponse.SC_OK);

        return "success";
    }

//    public String oauth2JwtHeaderSet(HttpServletRequest request, HttpServletResponse response) {
//        // 헤더에서 access 토큰을 가져옴
//        String access = request.getHeader("access");
//
//        // 토큰 유효성 검사
//        if (!StringUtils.hasText(access)) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            return "bad";
//        }
//
//        try {
//            // 여기서 토큰의 유효성 검사를 수행 (예: 만료, 위조 등 검사)
//            if (isTokenExpiredOrInvalid(access)) {
//                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return "unauthorized";
//            }
//
//            // 토큰이 유효하다면 헤더에 access 토큰 설정 및 클라이언트의 access 토큰 쿠키를 만료
//            response.addHeader("access", access);
//            response.addCookie(CookieUtil.createCookie("access", null, 0));
//            response.setStatus(HttpServletResponse.SC_OK);
//            return "success";
//
//        } catch (Exception e) {
//            // 토큰 검증 중 예외가 발생하면 예외 로그 출력 및 상태 코드 설정
//            e.printStackTrace();
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            return "error";
//        }
//    }
//
//    // 토큰의 만료 및 유효성 검사를 위한 메서드 (구현 필요)
//    private boolean isTokenExpiredOrInvalid(String token) {
//        // 토큰의 만료 및 유효성을 검사하는 로직을 구현하세요.
//        // 예를 들어 JWTUtil 클래스를 활용하여 토큰이 만료되었는지, 위조되었는지 등을 검사할 수 있습니다.
//        // 만약 토큰이 유효하지 않다면 true를 반환하고, 유효하다면 false를 반환합니다.
//        return false; // 이 부분을 실제 토큰 검증 로직으로 교체하세요.
//    }
}