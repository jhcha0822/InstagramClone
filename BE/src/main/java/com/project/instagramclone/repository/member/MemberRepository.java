package com.project.instagramclone.repository.member;

import com.project.instagramclone.entity.member.MemberEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // memberId로 존재 여부를 확인하는 메서드
    Boolean existsByUsername(String username);
    MemberEntity findByOauthId(String oauthId);
    Optional<MemberEntity> findByUsername(String username);
    Optional<MemberEntity> findByNickname(String nickname);
    Optional<MemberEntity> findByEmail(String email);

    List<MemberEntity> findByNicknameContainingIgnoreCase(String nickname);

    @Modifying
    @Query("UPDATE MemberEntity m SET m.nickname = :newNickname WHERE m.nickname = :currentNickname")
    int updateNickname(@Param("currentNickname") String currentNickname, @Param("newNickname") String newNickname);
}
