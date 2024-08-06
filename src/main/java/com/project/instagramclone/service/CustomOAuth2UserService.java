package com.project.instagramclone.service;

import com.project.instagramclone.dto.CustomOAuth2User;
import com.project.instagramclone.dto.GoogleResponse;
import com.project.instagramclone.dto.MemberDto;
import com.project.instagramclone.dto.OAuth2Response;
import com.project.instagramclone.entity.Member;
import com.project.instagramclone.repository.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    public CustomOAuth2UserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        System.out.println(oAuth2User);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if (registrationId.equals("naver")) {
            // oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        // 리소스 서버에서 발급 받은 정보로 사용자를 특정할 아이디값을 만듬
        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        Optional<Member> existData = memberRepository.findByUsername(username);

        if (existData == null) {
            Member member = new Member();
            member.setUsername(username);
            member.setEmail(oAuth2Response.getEmail());
            member.setNickname(oAuth2Response.getName());
            // member.setRole("ROLE_USER");
            memberRepository.save(member);

            MemberDto memberDto = new MemberDto();
            memberDto.setUsername(username);
            memberDto.setNickname(oAuth2Response.getName());
            memberDto.setEmail(oAuth2Response.getEmail());

            // memberDto.setName(oAuth2Response.getName());
            // memberDTO.setRole("ROLE_USER");

            return new CustomOAuth2User(memberDto);
        }
        else {
            existData.setEmail(oAuth2Response.getEmail());
            existData.setNickname(oAuth2Response.getName());

            memberRepository.save(existData);

            MemberDto memberDTO = new MemberDto();
            memberDTO.setUsername(existData.getUsername());
            memberDTO.setNickname(oAuth2Response.getName());
            // memberDTO.setRole(existData.getRole());

            return new CustomOAuth2User(memberDTO);
        }
    }
}