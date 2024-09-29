package com.project.instagramclone.entity.member;

import jakarta.persistence.*;
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
    @Column(name = "nickname")
    private String nickname;

    // 이메일
    @Column(name = "email")
    private String email;

    // 활성화 여부
    @Column(name = "activated")
    private boolean activated;

    // 권한
    @Column(name = "role")
    private String role;

    /*------ Form 전용 필드------*/

    // 아이디
    @Column(name = "username")
    private String username;

    // 비밀번호
    @Column(name = "password")
    private String password;

    /*--------------------------*/

    /*----- OAuth2 전용 필드 -----*/

    // OAuth2 제공자
    @Column(name = "oauth2Provider")
    private String oauth2Provider;

    // OAuth2 고유 ID (sub 값 등)
    @Column(name = "oauthId")
    private String oauthId;

    /*--------------------------*/

    @Builder
    public MemberEntity(String username, String password, String nickname,
                        String email, boolean activated, String role,
                        String oauth2Provider, String oauthId ) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.activated = activated;
        this.role = role;
        this.oauth2Provider = oauth2Provider;
        this.oauthId = oauthId;
    }

}
