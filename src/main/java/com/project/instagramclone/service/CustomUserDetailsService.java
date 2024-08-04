package com.project.instagramclone.service;

import com.project.instagramclone.dto.CustomUserDetails;
import com.project.instagramclone.entity.Member;
import com.project.instagramclone.entity.MemberDetail;
import com.project.instagramclone.repository.MemberDetailRepository;
import com.project.instagramclone.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberDetailRepository memberDetailRepository;

    public CustomUserDetailsService(
            MemberRepository memberRepository,
            MemberDetailRepository memberDetailRepository
    ) {
        this.memberRepository = memberRepository;
        this.memberDetailRepository = memberDetailRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> user = memberRepository.findByUsername(username);
        Optional<MemberDetail> userDetail = memberDetailRepository.findById(user.get().getMemberId());
        if(user.isPresent()) {
            return new CustomUserDetails(user, userDetail);
        }

        return null;
    }
}
