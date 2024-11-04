package com.project.instagramclone.service.member;

import com.project.instagramclone.entity.member.MemberEntity;
import com.project.instagramclone.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // nickname으로 username을 조회하는 메서드
    public String getUsernameByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).get().getNickname();
    }

    public MemberEntity getMemberByUsername(String username) {
        return memberRepository.findByUsername(username).get();
    }

    // nickname 업데이트 메서드
    @Transactional
    public int updateNickname(String currentNickname, String newNickname) {
        return memberRepository.updateNickname(currentNickname, newNickname);
    }

}
