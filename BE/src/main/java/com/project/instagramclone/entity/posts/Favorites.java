package com.project.instagramclone.entity.posts;

import com.project.instagramclone.entity.member.MemberEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "favorites")
@Getter
@Setter
@NoArgsConstructor
public class Favorites {
    @Id
    private String id;
    private String postId; // 게시글 ID
    private String memberId; // 유저 ID
}