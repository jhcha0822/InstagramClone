package com.project.instagramclone.dto.member;

import com.project.instagramclone.entity.member.MemberEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class CustomUserDetails implements UserDetails  {

    private final MemberEntity member;

    public CustomUserDetails(MemberEntity member) {
        this.member = member;
        if (this.member == null) {
            System.out.println("CustomUserDetails 생성 시 member가 null입니다.");
        } else {
            System.out.println("CustomUserDetails 생성 시 member: " + this.member.getUsername());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) member::getRole);
        return collection;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    public String getNickname() {
        return member.getNickname();
    }

    public Long getMemberId() {
        return member.getMemberId();
    }

    @Override
    public boolean isAccountNonExpired() {
        // return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
        // return UserDetails.super.isEnabled();
        return true;
    }
}
