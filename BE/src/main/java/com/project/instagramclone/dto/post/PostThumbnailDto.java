package com.project.instagramclone.dto.post;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
// 이미지 썸네일을 위한 DTO
public class PostThumbnailDto {
    private String mediaUrl;
    private String postId;

    public PostThumbnailDto(String mediaUrl, String postId) {
        this.mediaUrl = mediaUrl;
        this.postId = postId;
    }
}
