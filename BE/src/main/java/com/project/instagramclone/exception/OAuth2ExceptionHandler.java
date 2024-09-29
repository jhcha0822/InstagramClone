package com.project.instagramclone.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Spring Security 예외를 핸들링하는 코드
@ControllerAdvice
public class OAuth2ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2ExceptionHandler.class);

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<String> handleOAuth2Exception(OAuth2AuthenticationException ex) {
        // 예외 로그 기록
        logger.error("OAuth2 오류 발생: {}", ex.getMessage(), ex);

        // 필요에 따라 다른 상태 코드 반환
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OAuth2 오류 발생: " + ex.getMessage());
    }
}
