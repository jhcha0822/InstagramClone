package com.project.instagramclone.controller.post;

import com.project.instagramclone.dto.post.PostThumbnailDto;
import com.project.instagramclone.jwt.JWTUtil;
import com.project.instagramclone.service.post.FavoritesService;
import com.project.instagramclone.service.member.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name="Post service", description="Post Favorites service API")
public class FavoritesController {

    private final FavoritesService favoritesService;
    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    @GetMapping("/favorites")
    public List<PostThumbnailDto> getFavoritePosts(HttpServletRequest request) {
        Long memberId = extractUserIdFromToken(request);
        return favoritesService.getFavoritePost(memberId.toString());
    }

    @PostMapping("/favorites/{postId}")
    public void favoritePost(@PathVariable String postId, HttpServletRequest request) {
        Long memberId = extractUserIdFromToken(request);
        favoritesService.addFavorite(postId, memberId.toString());
    }

    @DeleteMapping("/favorites/{postId}")
    public void unfavoritePost(@PathVariable String postId, HttpServletRequest request) {
        Long memberId = extractUserIdFromToken(request);
        favoritesService.removeFavorite(postId, memberId.toString());
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
