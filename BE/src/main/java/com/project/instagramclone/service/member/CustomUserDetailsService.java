package com.project.instagramclone.service.member;

import com.project.instagramclone.dto.member.CustomUserDetails;
import com.project.instagramclone.entity.member.MemberEntity;
import com.project.instagramclone.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername 호출됨: " + username);
        Optional<MemberEntity> member = memberRepository.findByUsername(username);
        if (member.isPresent()) {
            System.out.println("조회된 member: " + member.get());
            return new CustomUserDetails(member.get());
        } else {
            System.out.println("사용자 찾기 실패: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public UserDetails loadUserByNickname(String nickname) throws UsernameNotFoundException {
        System.out.println("loadUserByNickname 호출됨: " + nickname);
        Optional<MemberEntity> member = memberRepository.findByNickname(nickname);
        if (member.isPresent()) {
            System.out.println("조회된 member: " + member.get());
            return new CustomUserDetails(member.get());
        } else {
            System.out.println("사용자 찾기 실패: " + nickname);
            throw new UsernameNotFoundException("User not found with username: " + nickname);
        }
    }

    public Long getMemberIdByUsername(String username) {
        // username으로 MemberEntity 조회
        MemberEntity entity = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 디버깅을 위한 로그 추가
        System.out.println("조회된 사용자: " + entity);

        return entity.getMemberId();
    }
}
