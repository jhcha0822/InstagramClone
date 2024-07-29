package com.project.instagramclone.dto;

import com.project.instagramclone.entity.User;
import com.project.instagramclone.entity.UserDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CustomUserDetails implements UserDetails {

    private final Optional<User> user;
    private final Optional<UserDetail> userDetail;

    public CustomUserDetails(
            Optional<com.project.instagramclone.entity.User> user,
            Optional<com.project.instagramclone.entity.UserDetail> userDetail
    ) {
        this.user = user;
        this.userDetail = userDetail;
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
    public String getPassword() {
        return userDetail.get().getPassword();
    }

    @Override
    public String getUsername() {
        return user.get().getUid();
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
