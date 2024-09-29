package com.project.instagramclone.service.member;

import com.project.instagramclone.dto.member.AccountUpdateDto;
import com.project.instagramclone.dto.member.PasswordChangeDto;
import com.project.instagramclone.entity.member.MemberEntity;
import com.project.instagramclone.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 정보 수정 로직
    public void updateAccount(Long memberId, AccountUpdateDto accountUpdateDto) {
        MemberEntity member = memberRepository.findById(memberId).orElseThrow();
        member.setNickname(accountUpdateDto.getNickname());
//        member.setProfilePic(accountUpdateDto.getProfilePic());]
//        member.setBio(accountUpdateDto.getBio());
        memberRepository.save(member);
    }

    // 비밀번호 변경 로직
    public boolean changePassword(Long memberId, PasswordChangeDto passwordChangeDto) {
        MemberEntity member = memberRepository.findById(memberId).orElseThrow();
        // OAuth2 기반 유저라면 비밀번호 변경 불가하게 처리 필요
        if (passwordEncoder.matches(passwordChangeDto.getCurrentPassword(), member.getPassword())) {
            member.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
            memberRepository.save(member);
            return true;
        }
        return false;
    }
}
