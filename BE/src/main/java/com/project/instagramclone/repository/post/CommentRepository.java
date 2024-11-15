package com.project.instagramclone.repository.post;

import com.project.instagramclone.entity.posts.Comments;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends MongoRepository<Comments, String> {
    // 부모 댓글만 조회하는 메서드
    List<Comments> findByPostIdAndParentCommentIdIsNull(String postId);

    // 특정 부모 댓글의 대댓글 조회
    List<Comments> findByParentCommentId(String parentCommentId);

}
