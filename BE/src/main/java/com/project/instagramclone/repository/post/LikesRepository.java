package com.project.instagramclone.repository.post;

import com.project.instagramclone.entity.posts.Likes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikesRepository extends MongoRepository<Likes, String> {
    //특정 게시글 좋아요 조회
    boolean existsByPostIdAndMemberId(String postId, String memberId);

    //특정 게시글 좋아요 삭제 
    void deleteByPostIdAndMemberId(String postId, String memberId);
}
