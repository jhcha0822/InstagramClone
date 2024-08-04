package com.project.instagramclone.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "member")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "memberId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "username")
    private String username;

    @Column(name = "nickname", length = 20, unique = true)
    private String nickname;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "activated")
    private boolean activated;

    // 비식별 1:N 관계 설정
    @ManyToOne
    @JoinColumn(name = "snsId")
    private Sns sns;
}
