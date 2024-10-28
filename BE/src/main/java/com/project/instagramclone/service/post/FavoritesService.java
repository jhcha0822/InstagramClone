package com.project.instagramclone.service.post;

import com.project.instagramclone.dto.post.PostThumbnailDto;
import com.project.instagramclone.entity.posts.Favorites;
import com.project.instagramclone.entity.posts.Posts;
import com.project.instagramclone.repository.post.FavoritesRepository;
import com.project.instagramclone.repository.post.PostImageRepository;
import com.project.instagramclone.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoritesService {

    private final FavoritesRepository favoritesRepository;
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    // 즐겨찾기(저장됨) 추가
    public void addFavorite(String postId, String memberId) {
        if (!favoritesRepository.existsByPostIdAndMemberId(postId, memberId)) {
            Favorites favorites = new Favorites();
            favorites.setPostId(postId);
            favorites.setMemberId(memberId);
            favoritesRepository.save(favorites);
        }
    }

    // 즐겨찾기(저장됨) 제거
    public void removeFavorite(String postId, String memberId) {
        favoritesRepository.deleteByPostIdAndMemberId(postId, memberId);
    }

    // 즐겨찾기(저장됨) 목록 가져오기
    public List<PostThumbnailDto> getFavoritePost(String memberId) {
        // memberId가 즐겨찾기한 게시글의 postId 목록 가져오기
        List<String> favoritePostIds = favoritesRepository.findByMemberId(memberId).stream()
                .map(Favorites::getPostId)
                .collect(Collectors.toList());

        // 해당 postIds를 통해 게시글 가져오기
        List<Posts> favoritePosts = postRepository.findAllById(favoritePostIds);

        // Posts 데이터를 PostThumbnailDto로 변환하여 반환
        return favoritePosts.stream()
                .flatMap(post -> postImageRepository.findByPostId(post.getId()).stream()
                        .map(postImage -> {
                            // mediaUrl이 List<String>일 경우 첫번째 이미지를 사용
                            String mediaUrl = (postImage.getMediaUrl() != null && !postImage.getMediaUrl().isEmpty())
                                    ? postImage.getMediaUrl().get(0) : null;

                            return new PostThumbnailDto(mediaUrl, post.getId());
                        }))
                .collect(Collectors.toList());
    }
}
