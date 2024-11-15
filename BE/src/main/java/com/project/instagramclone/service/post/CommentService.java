package com.project.instagramclone.service.post;

import com.project.instagramclone.dto.post.CommentDTO;
import com.project.instagramclone.entity.posts.Comments;
import com.project.instagramclone.repository.post.CommentRepository;
import com.project.instagramclone.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    public final CommentRepository commentRepository;
    public final PostRepository postRepository;

    // 댓글 작성
    public Comments createComment(CommentDTO commentDTO) {
        Comments comment = new Comments();
        comment.setPostId(commentDTO.getPostId());
        comment.setNickname(commentDTO.getNickname());
        comment.setComment(commentDTO.getComment());
        comment.setRegdate(Instant.now().getEpochSecond());
        comment.setChildren(new ArrayList<>());  // 자식 댓글 초기화
        return commentRepository.save(comment);
    }

    // 대댓글 작성
    public Comments createReply(CommentDTO commentDTO) {
        // 부모 댓글을 먼저 조회
        Comments parentComment = commentRepository.findById(commentDTO.getParentCommentId())
                .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다."));

        // 대댓글 생성
        Comments replyComment = new Comments();
        replyComment.setPostId(commentDTO.getPostId());
        replyComment.setParentCommentId(commentDTO.getParentCommentId());
        replyComment.setNickname(commentDTO.getNickname());
        replyComment.setComment(commentDTO.getComment());
        replyComment.setRegdate(Instant.now().getEpochSecond());
        replyComment.setChildren(new ArrayList<>());  // 대댓글의 children 필드는 빈 리스트로 초기화

        // 대댓글 저장
        replyComment = commentRepository.save(replyComment); // ID가 생성된 대댓글을 저장

        // 부모 댓글에 자식 댓글(대댓글) 추가 (ID가 아니라, 댓글 객체를 추가)
        parentComment.getChildren().add(replyComment);  // replyComment 객체 자체를 추가

        // 부모 댓글 업데이트
        commentRepository.save(parentComment);

        return replyComment;
    }




    // 게시글별 댓글 조회 (댓글 + 대댓글 포함)
    public List<Comments> getCommentsForPost(String postId) {
        // 부모 댓글만 조회
        List<Comments> parentComments = commentRepository.findByPostIdAndParentCommentIdIsNull(postId);

        // 각 부모 댓글에 대해 대댓글을 조회하고 추가
        for (Comments parentComment : parentComments) {
            List<Comments> replies = commentRepository.findByParentCommentId(parentComment.getId());
            parentComment.setChildren(replies);  // 대댓글 리스트를 children에 저장
        }

        return parentComments;  // 부모 댓글 목록만 반환 (대댓글은 children 필드에 포함됨)
    }

}