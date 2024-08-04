package com.project.instagramclone.dto;

import com.project.instagramclone.entity.Member;
import com.project.instagramclone.entity.MemberDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {

    private final Optional<Member> member;
    private final Optional<MemberDetail> memberDetail;

    public CustomUserDetails(
            Optional<Member> member,
            Optional<MemberDetail> memberDetail
    ) {
        this.member = member;
        this.memberDetail = memberDetail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collection = new ArrayList<>();
//        collection.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return userEntity.getRole();
//            }
//        });
//        return collection;
        return List.of();
    }

    @Override
    public String getUsername() {
        return member.get().getUsername();
    }

    @Override
    public String getPassword() {
        return memberDetail.get().getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
