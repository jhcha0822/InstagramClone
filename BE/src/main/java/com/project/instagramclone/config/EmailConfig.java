package com.project.instagramclone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    // yaml에서 정보 가져오기
    @Value("${spring.mail.username}") private String username;
    @Value("${spring.mail.password}") private String password;
    @Value("${spring.mail.host}") private String host;
    @Value("${spring.mail.port}") private int port;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);           // smtp 서버 주소
        mailSender.setPort(port);           // TLS port
        mailSender.setUsername(username);   // 발신 메일 아이디
        mailSender.setPassword(password);   // 발신 메일 비밀번호
        
        mailSender.setDefaultEncoding("UTF-8");            // 인코딩 설정
        mailSender.setJavaMailProperties(getProperties()); // 메일 인증서버의 정보
        
        return mailSender;
    }

    private Properties getProperties() {
        Properties properties = new Properties();

        properties.put("mail.transport.protocol", "smtp");      // 프로토콜 설정
        properties.put("mail.smtp.auth", "true");               // smtp 인증
        properties.put("mail.smtp.starttls.enable", "true");    // smtp starttls 사용
        properties.put("mail.smtp.ssl.trust", host);            // ssl 인증 서버 주소
        properties.put("mail.smtp.ssl.enable", "false");         // ssl 사용 안함
        properties.put("mail.debug", "true");                   // 디버그 사용

        return properties;
    }
}
