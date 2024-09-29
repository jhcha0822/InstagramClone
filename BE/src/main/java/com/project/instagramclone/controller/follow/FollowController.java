package com.project.instagramclone.controller.follow;

import com.project.instagramclone.dto.follows.FollowDto;
import com.project.instagramclone.dto.member.CustomUserDetails;
import com.project.instagramclone.service.follows.FollowService;
import com.project.instagramclone.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name="follow service", description="follow service API")
public class FollowController {

    private final FollowService followService;
    private final MemberService memberService;

    @Operation(summary = "팔로우 요청", description = "특정 사용자를 팔로우합니다.")
    @PostMapping("/{followerNickname}/follow/{followingNickname}")
    public ResponseEntity<?> follow(@PathVariable String followerNickname, @PathVariable String followingNickname, Authentication authentication) {
        if (!followerNickname.equals(((CustomUserDetails) authentication.getPrincipal()).getNickname())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }
        try {
            // 닉네임을 통해 Service에서 팔로우 처리
            followService.follow(followerNickname, followingNickname);
            return ResponseEntity.ok("팔로우 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "언팔로우 요청", description = "특정 사용자를 언팔로우합니다.")
    @DeleteMapping("/{followerNickname}/unfollow/{followingNickname}")
    public ResponseEntity<String> unfollow(@PathVariable String followerNickname, @PathVariable String followingNickname, Authentication authentication) {
        if (!followerNickname.equals(((CustomUserDetails) authentication.getPrincipal()).getNickname())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("권한이 없습니다.");
        }
        try {
            // 닉네임을 통해 Service에서 팔로우 처리
            followService.unfollow(followerNickname, followingNickname);
            return ResponseEntity.ok("언팔로우 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "팔로워 목록 조회", description = "특정 사용자를 팔로우하는 모든 팔로워 목록을 조회합니다.")
    @GetMapping("/{nickname}/followers")
    public ResponseEntity<List<FollowDto>> getFollowers(@PathVariable String nickname, Authentication authentication) {
        if (nickname == null || !nickname.equals(((CustomUserDetails) authentication.getPrincipal()).getNickname())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // member의 닉네임과 인증된 사용자 이름이 일치하지 않으면 접근 금지
        if (!nickname.equals(((CustomUserDetails) authentication.getPrincipal()).getNickname())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // 팔로워 목록 조회
        List<FollowDto> followers = followService.getFollowers(nickname);
        return ResponseEntity.ok(followers);
    }

    @Operation(summary = "팔로우 목록 조회", description = "특정 사용자가 팔로우하는 모든 팔로우 목록을 조회합니다.")
    @GetMapping("/{nickname}/following")
    public ResponseEntity<List<FollowDto>> getFollowing(@PathVariable String nickname, Authentication authentication) {
        if (nickname == null || !nickname.equals(((CustomUserDetails) authentication.getPrincipal()).getNickname())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        // member의 닉네임과 인증된 사용자 이름이 일치하지 않으면 접근 금지
        if (!nickname.equals(((CustomUserDetails) authentication.getPrincipal()).getNickname())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<FollowDto> following = followService.getFollowing(nickname);
        return ResponseEntity.ok(following);
    }
}
