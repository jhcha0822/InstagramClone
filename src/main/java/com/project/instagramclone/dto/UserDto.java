package com.project.instagramclone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.instagramclone.entity.UserDetail;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import com.project.instagramclone.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    // 회원가입 시 사용

    @NotNull
    @Size(min = 3, max = 50)
    private String uid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    private String nickname;

    @NotNull
    @Size(min = 3, max = 50)
    private String email;

    // private String profileImageUrl;

    public static UserDto from(User user, UserDetail userDetail) {
        if(user == null) return null;

        return UserDto.builder()
                .uid(user.getUid())
                .password(userDetail.getPassword())
                .nickname(user.getNickname())
                .email(user.getEmail())
                // .profileImgUrl(user.getUserDetail().getProfileImgUrl())
                .build();
    }
}