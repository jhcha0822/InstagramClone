package com.project.instagramclone.service.post;

import com.project.instagramclone.entity.posts.Likes;
import com.project.instagramclone.repository.post.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;

    //좋아요
    public void addLike(String postId, String memberId) {
        if (!likesRepository.existsByPostIdAndMemberId(postId, memberId)) {
            Likes like = new Likes();
            like.setPostId(postId);
            like.setMemberId(memberId);
            likesRepository.save(like);
        }
    }

    //좋아요 취소
    public void removeLike(String postId, String memberId) {
        likesRepository.deleteByPostIdAndMemberId(postId, memberId);
    }
}

