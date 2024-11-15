package com.project.instagramclone.entity.posts;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "comments")
@Getter
@Setter
@NoArgsConstructor
public class Comments {
    @Id
    private String id;
    private String nickname;
    private String comment;

    private String postId;
    private String parentCommentId; // 대댓글의 경우 부모 댓글의 ID, 없으면 null
    private long regdate;

    @OneToMany(fetch = FetchType.LAZY)  // 자식 댓글은 지연 로딩으로 처리 (필요시 즉시 로딩 가능)
    @JoinColumn(name = "parentCommentId", referencedColumnName = "id", insertable = false, updatable = false)  // 부모 댓글과 연결
    private List<Comments> children;  // 부모 댓글의 자식 댓글들
}
