package com.project.instagramclone.dto.member;

import com.project.instagramclone.dto.post.PostThumbnailDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder

@AllArgsConstructor
@NoArgsConstructor
// 프로필 페이지 구성을 위한 DTO
public class ProfileDto {

    private String nickname;
    private String profilePic;
    private String bio;
    private int postCount;
    private int followerCount;
    private int followingCount;
    private List<PostThumbnailDto> postThumbnails;

}
