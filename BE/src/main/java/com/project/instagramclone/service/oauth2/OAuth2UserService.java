package com.project.instagramclone.service.oauth2;

import com.project.instagramclone.dto.oauth2.CustomOAuth2User;
import com.project.instagramclone.dto.oauth2.OAuth2UserDto;
import com.project.instagramclone.entity.member.MemberEntity;
import com.project.instagramclone.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2User findByUsername(String username) {
        MemberEntity entity = memberRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (entity == null) {
            log.error("User not found with username: {}", username);
            return null; // 또는 사용자 정의 예외를 던질 수 있습니다.
        }

        OAuth2UserDto userDto = OAuth2UserDto.builder()
                .memberId(entity.getMemberId())
                .nickname(entity.getNickname())
                .email(entity.getEmail())
                .role(entity.getRole())
                .build();

        return new CustomOAuth2User(userDto);
    }

    public void updateNickname(String username, String nickname) {
        int updatedRows = memberRepository.updateNickname(username, nickname); // 명시적으로 update 쿼리 실행
        if (updatedRows == 0) {
            log.warn("Nickname update failed for username: {}", username);
            // 필요에 따라 사용자 정의 예외를 던질 수 있습니다.
        }
    }

}
