package com.project.instagramclone.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;

    // 메일 전송
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("jhcha0822@hanyang.ac.kr"); // 발신자 이메일 주소
        mailSender.send(message);
    }

    // 보안 코드 생성 및 이메일 발송
    public void sendSecurityCode(String email) {
        String securityCode = generateSecurityCode(); // 보안 코드 생성 메서드
        saveSecurityCodeToRedis(email, securityCode); // Redis에 보안 코드 저장
        sendEmail(email, "Your Security Code", "Your security code is: " + securityCode); // 보안 코드 이메일 전송
    }

    // 보안 코드 생성
    private String generateSecurityCode() {
        return String.format("%06d", new Random().nextInt(999999)); // 6자리 보안 코드 생성
    }

    // Redis에 보안 코드 저장
    private void saveSecurityCodeToRedis(String email, String securityCode) {
        redisTemplate.opsForValue().set(email, securityCode, Duration.ofMinutes(10)); // 유효기간 10분
    }

    // 보안 코드 검증
    public boolean verifySecurityCode(String email, String code) {
        String savedCode = redisTemplate.opsForValue().get(email);
        return code.equals(savedCode);
    }
}
