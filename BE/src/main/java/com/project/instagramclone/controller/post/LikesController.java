package com.project.instagramclone.controller.post;

import com.project.instagramclone.jwt.JWTUtil;
import com.project.instagramclone.service.member.MemberService;
import com.project.instagramclone.service.post.LikesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name="Post service", description="Post Likes service API")
public class LikesController {
    private final LikesService likesService;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @PostMapping("/likes/{postId}")
    public void likePost(@PathVariable String postId, HttpServletRequest request) {
        Long memberId = extractUserIdFromToken(request);
        likesService.addLike(postId, memberId.toString());
    }

    @DeleteMapping("/likes/{postId}")
    public void unlikePost(@PathVariable String postId, HttpServletRequest request) {
        Long memberId = extractUserIdFromToken(request);
        likesService.removeLike(postId, memberId.toString());
    }

    private long extractUserIdFromToken(HttpServletRequest request) {
        // Authorization 헤더에서 토큰 가져오기
        String token = request.getHeader("Authorization");

        // 토큰에서 Bearer 부분을 제거
        String actualToken = token.replace("Bearer ", "");

        // 토큰에서 닉네임 추출
        String nickname = jwtUtil.getNickname(actualToken);

        // 닉네임으로 회원 인덱스 검색
        return memberService.getMemberIdByNickname(nickname);
    }
}
