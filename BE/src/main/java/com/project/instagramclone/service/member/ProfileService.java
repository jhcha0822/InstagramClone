package com.project.instagramclone.service.member;

import com.project.instagramclone.dto.member.ProfileDto;
import com.project.instagramclone.dto.post.PostThumbnailDto;
import com.project.instagramclone.entity.member.MemberEntity;
import com.project.instagramclone.entity.posts.Posts;
import com.project.instagramclone.repository.follows.FollowsRepository;
import com.project.instagramclone.repository.member.MemberRepository;
import com.project.instagramclone.repository.post.PostImageRepository;
import com.project.instagramclone.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProfileService {

    private final MemberRepository memberRepository;
    private final FollowsRepository followsRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    public ProfileDto getProfileByNickname(String nickname) {
        // nickname을 가진 유저 존재여부 확인
        Optional<MemberEntity> member = Optional.ofNullable(memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다.")));

        // 해당 유저의 모든 게시물 조회
        List<Posts> posts = postRepository.findPostsByNickname(nickname);

        // 각 게시물의 postImage 목록 조회, DTO로 변환
        List<PostThumbnailDto> thumbnails = posts.stream()
                .flatMap(post -> postImageRepository.findByPostId(post.getId()).stream()
                                .map(postImage -> {
                                    // mediaUrl이 List<String>일 경우 첫번째 이미지를 사용
                                    String mediaUrl = (postImage.getMediaUrl() != null && !postImage.getMediaUrl().isEmpty())
                                            ? postImage.getMediaUrl().get(0) : null;

                                    return new PostThumbnailDto(mediaUrl, post.getId());
                                }))
                .collect(Collectors.toList());

        // 게시글 수, 팔로워 수, 팔로잉 수
        int postCount = posts.size();
        int followerCount = followsRepository.findAllByFollowing_MemberId(member.get().getMemberId()).size();
        int followingCount = followsRepository.findAllByFollower_MemberId(member.get().getMemberId()).size();

        return new ProfileDto(
                member.get().getNickname(),
                member.get().getProfilePic(),
                member.get().getBio(),
                postCount,
                followerCount,
                followingCount,
                thumbnails
        );

    }

}
