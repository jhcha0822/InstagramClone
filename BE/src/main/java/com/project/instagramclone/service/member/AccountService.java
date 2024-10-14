package com.project.instagramclone.service.member;

import com.project.instagramclone.dto.member.AccountUpdateDto;
import com.project.instagramclone.dto.member.PasswordChangeDto;
import com.project.instagramclone.entity.member.MemberEntity;
import com.project.instagramclone.repository.member.MemberRepository;
import com.project.instagramclone.service.post.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    // 사용자 정보 조회 로직
    public AccountUpdateDto getAccount(Long memberId) {
        MemberEntity member = memberRepository.findById(memberId).orElseThrow();
        AccountUpdateDto accountUpdateDto = new AccountUpdateDto();
        accountUpdateDto.setNickname(member.getNickname());
        accountUpdateDto.setBio(member.getBio());
        return accountUpdateDto;
    }

    // 사용자 정보 수정 로직
    @Transactional
    public void updateAccount(Long memberId, AccountUpdateDto accountUpdateDto, MultipartFile profilePicFile) throws IOException {
        MemberEntity member = memberRepository.findById(memberId).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        member.setNickname(accountUpdateDto.getNickname());
        member.setBio(accountUpdateDto.getBio());

        // 프로필 사진 업데이트 (S3에 업로드)
        if (profilePicFile != null && !profilePicFile.isEmpty()) {
            String profilePicUrl = fileStorageService.saveFile(profilePicFile); // S3에 이미지 업로드 후 URL 반환
            member.setProfilePic(profilePicUrl); // 프로필 사진 URL 저장
        }

        memberRepository.save(member); // 변경 사항 저장
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
