package com.project.instagramclone.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final MemberDto memberDto;

    public CustomOAuth2User(MemberDto memberDto) {
        this.memberDto = memberDto;
    }

    @Override
    public Map<String, Object> getAttributes() {
        // 여러 IDP의 Attribute가 다양해 하나로 획일화가 어려워 사용 안함
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collection = new ArrayList<>();
//        collection.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return userDTO.getRole();
//            }
//        });
//        return collection;
        return null;
    }

    @Override
    public String getName() {
        return memberDto.getNickname();
    }

    public String getUsername() {
        return memberDto.getUsername();
    }
}
