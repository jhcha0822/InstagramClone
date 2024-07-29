package com.project.instagramclone.service;

import com.project.instagramclone.dto.CustomUserDetails;
import com.project.instagramclone.entity.User;
import com.project.instagramclone.entity.UserDetail;
import com.project.instagramclone.repository.UserDetailRepository;
import com.project.instagramclone.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    public CustomUserDetailsService(
            UserRepository userRepository,
            UserDetailRepository userDetailRepository
    ) {
        this.userRepository = userRepository;
        this.userDetailRepository = userDetailRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUid(uid);
        Optional<UserDetail> userDetail = userDetailRepository.findById(user.get().getUserId());
        if(user.isPresent()) {
            return new CustomUserDetails(user, userDetail);
        }

        return null;
    }
}
