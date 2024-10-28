package com.project.instagramclone.service.member;

import com.project.instagramclone.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // nickname으로 username을 조회하는 메서드
    public String getUsernameByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).get().getNickname();
    }

    // nickname 업데이트 메서드
    @Transactional
    public int updateNickname(String currentNickname, String newNickname) {
        return memberRepository.updateNickname(currentNickname, newNickname);
    }
    
    // nickname으로 memberId 조회
    public long getMemberIdByNickname(String nickname) {
        return memberRepository.findByNickname(nickname).get().getMemberId();
    }

}
