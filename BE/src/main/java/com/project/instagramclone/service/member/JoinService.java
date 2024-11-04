package com.project.instagramclone.service.member;

import com.project.instagramclone.dto.member.JoinDto;
import com.project.instagramclone.entity.member.MemberEntity;
import com.project.instagramclone.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class JoinService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;

    // 회원 가입 처리
    public void join(JoinDto joinDto) {

        Boolean isExist = memberRepository.existsByUsername(joinDto.getUsername());

        if (isExist) {
            System.out.println("이미 존재하는 회원입니다.");
            return;
        }

        new MemberEntity();

        MemberEntity member = MemberEntity
                .builder()
                .username(joinDto.getUsername())
                .password(bCryptPasswordEncoder.encode(joinDto.getPassword()))
                .nickname(joinDto.getNickname())
                .email(joinDto.getEmail())
                .activated(true)
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);
    }
}