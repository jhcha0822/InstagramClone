package com.project.instagramclone.entity.member;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "member")
@Getter
@Setter
@NoArgsConstructor
public class MemberEntity {

    @Id
    @Column(name = "memberId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    // 회원 이름
    @NotNull
    @Column(name = "nickname", unique = true , nullable = false)
    private String nickname;

    // 이메일
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    // 활성화 여부
    @NotNull
    @Column(name = "activated", nullable = false)
    private boolean activated;

    // 권한
    @NotNull
    @Column(name = "role", nullable = false)
    private String role;

    // 프로필 이미지
    @Nullable
    @Column(name = "profilePic", nullable = true)
    private String profilePic;

    // 프로필 소개글
    @Nullable
    @Column(name = "bio", nullable = true)
    private String bio;

    /*------ Form 전용 필드------*/

    // 아이디
    @Nullable
    @Column(name = "username", unique = true, nullable = true)
    private String username;

    // 비밀번호
    @Nullable
    @Column(name = "password", unique = true, nullable = true)
    private String password;

    /*--------------------------*/

    /*----- OAuth2 전용 필드 -----*/

    // OAuth2 제공자
    @Nullable
    @Column(name = "oauth2Provider", nullable = true)
    private String oauth2Provider;

    // OAuth2 고유 ID (sub 값 등)
    @Nullable
    @Column(name = "oauthId", unique = true, nullable = true)
    private String oauthId;

    /*--------------------------*/

    @Builder
    public MemberEntity(String username, String password, String nickname,
                        String email, boolean activated, String role,
                        String profilePic, String bio,
                        String oauth2Provider, String oauthId ) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.activated = activated;
        this.role = role;
        this.profilePic = profilePic;
        this.bio = bio;
        this.oauth2Provider = oauth2Provider;
        this.oauthId = oauthId;
    }

}
