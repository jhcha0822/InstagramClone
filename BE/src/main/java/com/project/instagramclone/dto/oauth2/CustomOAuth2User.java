package com.project.instagramclone.dto.oauth2;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2UserDto oAuth2UserDto;

    // OAuth2UserDto를 인자로 받는 생성자 추가
    public CustomOAuth2User(OAuth2UserDto oAuth2UserDto) {
        this.oAuth2UserDto = oAuth2UserDto;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("nickname", oAuth2UserDto.getNickname()); // 노출할 정보를 추가
        return attributes;
    }

    @Override
    public String getName() {
        return oAuth2UserDto.getNickname();  // 닉네임 반환
    }

    public String getNickname() {
        return oAuth2UserDto.getNickname();
    }

    public void setNickname(String nickname) {
        this.oAuth2UserDto.setNickname(nickname); // DTO의 닉네임 변경
    }

    public Long getMemberId() {
        return oAuth2UserDto.getMemberId();
    }

    public String getOauth2Id() {
        return oAuth2UserDto.getOauth2Id();
    }

    public String getEmail() {
        return oAuth2UserDto.getEmail();
    }

    // 유저의 권한 (ROLE_USER 등)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(oAuth2UserDto.getRole()));
    }

    public OAuth2UserDto getUserDto() {
        return oAuth2UserDto;
    }

}
