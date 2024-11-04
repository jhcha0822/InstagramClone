package com.project.instagramclone.controller.mail;

import com.project.instagramclone.service.mail.MailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name="Mail service", description="mailing service API")
public class MailController {

    private final MailService mailService;
    private final RedisTemplate<String, String> redisTemplate;

    // 이메일 전송 엔드포인트 (테스트)
    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam String to) {
        try {
            mailService.sendEmail(to, "제목 없음", "테스트 메일입니다.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Email sent successfully!";
    }
    
    // 이메일 인증 코드 전송 엔드포인트
    @PostMapping("/sendSecurityCode")
    public ResponseEntity<String> sendSecurityCode(@RequestParam String email) {
        mailService.sendSecurityCode(email);
        return ResponseEntity.ok("Security code sent to " + email);
    }

    // 이메일 인증 코드 검증 엔드포인트
    @PostMapping("/verifySecurityCode")
    public ResponseEntity<String> verifySecurityCode(@RequestParam String username, @RequestParam String email, @RequestParam String code) {
        String storedCode = redisTemplate.opsForValue().get(email);
        if (storedCode != null && storedCode.equals(code)) {
            return ResponseEntity.ok("Security code verified");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired security code");
        }
    }
    
}
