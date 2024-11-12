package com.project.instagramclone.repository.post;
import com.project.instagramclone.entity.posts.Favorites;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface FavoritesRepository extends MongoRepository<Favorites, String>{
    // 특정 게시글 즐겨찾기 조회
    boolean existsByPostIdAndMemberId(String postId, String memberId);
    // 특정 게시글 즐겨찾기 삭제
    void deleteByPostIdAndMemberId(String postId, String memberId);
    // 즐겨찾기(저장된) 게시글 목록 조회
    List<Favorites> findByMemberId(String memberId);
}