package com.project.instagramclone.service.oauth2;

import com.project.instagramclone.dto.oauth2.CustomOAuth2User;
import com.project.instagramclone.dto.oauth2.OAuth2UserDto;
import com.project.instagramclone.entity.member.MemberEntity;
import com.project.instagramclone.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // userRequest -> registration 정보
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // OAuth2 제공자 이름 가져오기
        String provider = userRequest.getClientRegistration().getClientName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 유효한 provider인지 확인
        if (!isValidProvider(provider)) {
            throw new OAuth2AuthenticationException("유효하지 않은 OAuth2 제공자입니다.");
        }

        String email = oAuth2User.getAttribute("email");
        String sub = oAuth2User.getAttribute("sub");

        // 유효성 검사
        if (email == null || email.isEmpty()) {
            throw new OAuth2AuthenticationException("유효하지 않은 이메일입니다.");
        }
        if (sub == null || sub.isEmpty()) {
            throw new OAuth2AuthenticationException("유효하지 않은 OAuth2 ID입니다.");
        }

        // 유저가 존재하는지 확인
        MemberEntity member = memberRepository.findByOauthId(sub);
        if (member == null) {
            // 새로운 유저 등록
            member = new MemberEntity();
            member.setEmail(email);
            member.setOauthId(sub);
            member.setNickname("TempNickname"); // 처음 로그인 시 nickname을 빈칸으로 설정
            member.setOauth2Provider(provider);
            member.setActivated(true); // 필요한 경우 활성화 상태를 설정
            member.setRole("ROLE_USER"); // 기본 역할 설정
            member.setUsername(null); // ID를 빈 문자열로 설정
            member.setPassword(null); // 비밀번호를 빈 문자열로 설정

            try {
                // 실제 데이터베이스 저장
                member = memberRepository.save(member);
            } catch (Exception e) {
                // 예외 로그 출력
                e.printStackTrace();
                throw new OAuth2AuthenticationException("사용자 저장 중 오류가 발생했습니다: " + e.getMessage());
            }
        }

        // 이미 존재하는 유저는 DTO로 변환하여 반환
        OAuth2UserDto oAuth2UserDto = OAuth2UserDto.builder()
                .memberId(member.getMemberId())
                .oauth2Id(member.getOauthId())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .role(member.getRole())
                .build();

        return new CustomOAuth2User(oAuth2UserDto);
    }

    // 유효한 OAuth2 제공자인지 확인하는 메서드
    private boolean isValidProvider(String provider) {
        return provider.equals("google") || provider.equals("github");
    }
}
