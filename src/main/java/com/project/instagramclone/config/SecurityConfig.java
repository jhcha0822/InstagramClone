package com.project.instagramclone.config;

import com.project.instagramclone.jwt.JwtFilter;
import com.project.instagramclone.jwt.JwtUtil;
import com.project.instagramclone.jwt.LoginFilter;
import com.project.instagramclone.service.CustomOAuth2UserService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@EnableMethodSecurity // @PreAuthorize 어노테이션을 메서드단위로 추가하기 위함
@Configuration
public class SecurityConfig {

    // 객체 생성자 주입
    private final CorsFilter corsFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig( // 생성한 클래스들을 주입받음
        CorsFilter corsFilter,
        AuthenticationConfiguration authenticationConfiguration,
        JwtUtil jwtUtil,
        CustomOAuth2UserService customOAuth2UserService
    ) {
        this.corsFilter = corsFilter;
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    // AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // token을 사용하는 방식이기 때문에 csrf를 disable
                // JWT는 세션을 stateless 하게 관리함
                // 세션 방식의 경우 세션이 고정되어야 하기에 csrf enable
                .csrf(AbstractHttpConfigurer::disable)

                // JWT를 이용해 로그인을 하기에 formLogin과 basic 인증 방식 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // OAuth2
                // .oauth2Login(Customizer.withDefaults())
                .oauth2Login((oauth2) -> oauth2
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)))

                // CORS Filter 등록
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // JwtFilter 등록
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)
                // 커스텀한 로그인 필터를 UsernamePasswordAuthenticationFilter 자리에 등록
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class)

                // HttpServletRequest를 사용하는 요청에 대한 접근제한 설정
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/api/signup", "/api/login").permitAll()           // 로그인과 토큰 요청 시 토큰이 없기에 허용
                        .requestMatchers("/").permitAll()                                     // OAuth2 개발 중에 이용하기 위함
                        .requestMatchers(PathRequest.toH2Console()).permitAll()                 // H2콘솔 허용
                        .anyRequest().authenticated()                                           // 나머지 요청에 대해서 인증 필요
                )

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // enable h2-console
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                );

        return http.build();
    }
}