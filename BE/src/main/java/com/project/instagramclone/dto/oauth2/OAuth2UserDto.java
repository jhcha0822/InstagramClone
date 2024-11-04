package com.project.instagramclone.dto.oauth2;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2UserDto {

    private Long memberId;
    private String oauth2Id;
    private String nickname;
    private String email;
    private String role;

    @Builder
    public OAuth2UserDto(Long memberId, String oauth2Id, String username, String nickname, String email, String role) {
        this.memberId = memberId;
        this.oauth2Id = oauth2Id;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }
}
