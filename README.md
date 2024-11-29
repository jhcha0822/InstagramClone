# InstagramClone: 인스타그램 클론 프로젝트

## 목차
1. [프로젝트 개요](#프로젝트-개요)
2. [프로젝트 목적](#프로젝트-목적)
3. [개발기간 및 인원](#개발-기간-및-인원)
4. [주요 구현 기능 목록](#주요-구현-기능-목록)
5. [기술 스택](#기술-스택)
6. [프로젝트 구조](#프로젝트-구조)
7. [개발 과정](#개발-과정)
8. [이슈 및 트러블슈팅](#이슈-및-트러블슈팅)
9. [리팩토링 계획](#리팩토링-계획)
10. [References](#References)

## 프로젝트 개요

**InstagramClone**은 Spring Boot와 React를 사용하여 Instagram과 유사한 소셜 미디어 플랫폼을 구현한 클론 코딩 프로젝트입니다.  
주요 기능은 회원가입, 로그인, 게시물 작성/수정/삭제, 팔로우, DM 등이 포함됩니다.

<div align="right">
  <a href="#목차">맨 위로</a>
</div>

***

## 프로젝트 목적

This project was created to solidify our understanding of Spring Boot, explore other services, and complete the full development-to-deployment cycle.

Spring Boot에 대해 학습하며 이를 숙달하기 위해 프로젝트를 진행하였습니다.  
적용해보고 싶은 서비스들을 추가하였고, 이를 배포까지 해보는 과정을 통해 현업의 개발-배포 사이클을 학습하였습니다.

<div align="right">
  <a href="#목차">맨 위로</a>
</div>

***

## 개발 기간 및 인원

2024.08 ~ 2024.11 (4개월)

차정현[BE] jhcha0822@gmail.com <br>
Authentication, Security, Mailing, Deployment

윤성진[BE] ckwdktek12@gmail.com <br>
Post, Comments, Messaging, Deployment

<div align="right">
  <a href="#목차">맨 위로</a>
</div>

***

## 주요 구현 기능 목록

[배포 사이트](http://3.37.125.32/) : 닫힌 상태

### 회원가입 및 로그인
- 회원가입 시 이메일 인증 기능 제공
- 로그인 방식: 일반 로그인 및 OAuth2 (Google)
    
  회원가입  
  ![join](https://github.com/user-attachments/assets/87c25568-e9db-42be-8158-116ff7cf02f6)
  
  로그인(ID/PW 방식)  
  ![login_form](https://github.com/user-attachments/assets/1762c81f-cc4a-4370-9c6c-38bd1cbe73cb)

### 게시글 및 프로필 관리
- 게시글 작성 및 조회 기능 제공
- 프로필 검색 및 조회를 통해 작성한 게시글 확인 가능
- 댓글/대댓글, 좋아요, 즐겨찾기 기능 제공
    
  게시글 작성, 프로필 페이지  
  ![post_profile](https://github.com/user-attachments/assets/f478d7e0-1108-4c45-baf5-72ab8a8e203b)

  댓글/대댓글  
  ![reply](https://github.com/user-attachments/assets/7bb5461c-cd74-403e-b6cf-0b89b7222569)

  즐겨찾기, 좋아요  
  ![favorites](https://github.com/user-attachments/assets/f7de032f-1153-488a-a333-1fe797e22c55)

### 팔로우 기능
- 특정 사용자에 대한 팔로우/언팔로우 기능 제공
- 팔로우한 사용자들을 메인 화면에서 피드로 제공

  팔로우  
  ![follow](https://github.com/user-attachments/assets/22385142-08c0-4ce8-ad4c-5f9755d0448f)

### DM 기능
- 실시간 채팅 기능 제공

  DM(채팅)  
  ![DM](https://github.com/user-attachments/assets/2a5b8961-ac82-49c1-ab5e-4542c000541d)

<div align="right">
  <a href="#목차">맨 위로</a>
</div>
  
***

## 기술 스택

<table style="width: 100%;">
  <tbody>
    <tr>
      <th rowspan="4">⚛Front-end</th>
      <td>Language</td>
      <td>JS</td>
    </tr>
    <tr>
      <td>Library</td>
      <td>React</td>
    </tr>
    <tr>
      <td>Asynchronous</td>
      <td>Axios</td>
    </tr>
    <tr>
      <td>Messaging</td>
      <td>SockJS, StompJS</td>
    </tr>
    <tr>
      <th rowspan="9">🌱Back-end</th>
      <td>Language</td>
      <td>Java 17</td>
    </tr>
    <tr>
      <td>Framework</td>
      <td>Spring Boot 3.3.1</td>
    </tr>
    <tr>
      <td>ORM</td>
      <td>Spring Data JPA</td>
    </tr>
    <tr>
      <td>Build</td>
      <td>Gradle</td>
    </tr>
    <tr>
      <td>SMTP</td>
      <td>Gmail SMTP</td>
    </tr>
    <tr>
      <td>Authorization</td>
      <td>Spring Security, JWT</td>
    </tr>
    <tr>
      <td>Messaging</td>
      <td>WebSocket, STOMP</td>
    </tr>
    <tr>
      <td>API Documentation</td>
      <td>Swagger</td>
    </tr>
    <tr>
      <td>Database</td>
      <td>MySQL, H2, Redis, MongoDB</td>
    </tr>
    <tr>
      <th rowspan="3">👨‍👩‍👦‍👦Collaboration</th>
      <td>Api Test</td>
      <td>Postman</td>
    </tr>
    <tr>
      <td>Communication</td>
      <td>Discord, Google SpreadSheet</td>
    </tr>
    <tr>
      <td>Version Control</td>
      <td>Github</td>
    </tr>
    <tr>
      <th>🛠Deployment</th>
      <td colspan="2">EC2, RDS, S3, ElastiCache, VPC, MongoDB Atlas</td>
    </tr>
  </tbody>
</table>

<div align="right">
  <a href="#목차">맨 위로</a>
</div>

***

## 프로젝트 구조

### 디렉토리 구조

<details>
  <summary>Project Structure(클릭해 열기)</summary>
  <br>
  <code>
    \---src
    +---main
    |   +---java
    |   |   \---com
    |   |       \---project
    |   |           \---instagramclone
    |   |               |   InstagramCloneApplication.java
    |   |               |   
    |   |               +---Component
    |   |               |       MessageListener.java
    |   |               |       
    |   |               +---config
    |   |               |       CorsMvcConfig.java
    |   |               |       EmailConfig.java
    |   |               |       RedisConfig.java
    |   |               |       S3Config.java
    |   |               |       SecurityConfig.java
    |   |               |       SQSConfig.java
    |   |               |       SwaggerConfig.java
    |   |               |       WebSocketConfig.java
    |   |               |       
    |   |               +---controller
    |   |               |   +---chat
    |   |               |   |       ChatRoomController.java
    |   |               |   |       MessageController.java
    |   |               |   |       
    |   |               |   +---follow
    |   |               |   |       FollowController.java
    |   |               |   |       
    |   |               |   +---mail
    |   |               |   |       MailController.java
    |   |               |   |       
    |   |               |   +---member
    |   |               |   |       AccountController.java
    |   |               |   |       JoinController.java
    |   |               |   |       OAuth2Controller.java
    |   |               |   |       ProfileController.java
    |   |               |   |       ReissueController.java
    |   |               |   |       
    |   |               |   \---post
    |   |               |           CommentController.java
    |   |               |           FavoritesController.java
    |   |               |           LikesController.java
    |   |               |           PostController.java
    |   |               |           
    |   |               +---dto
    |   |               |   |   ErrorDto.java
    |   |               |   |   LoginDto.java
    |   |               |   |   
    |   |               |   +---account
    |   |               |   |       AccountUpdateDto.java
    |   |               |   |       PasswordChangeDto.java
    |   |               |   |       
    |   |               |   +---chat
    |   |               |   |       ChatMessageDTO.java
    |   |               |   |       CreateRoomRequestDTO.java
    |   |               |   |       MessageDTO.java
    |   |               |   |       MessageRequest.java
    |   |               |   |       
    |   |               |   +---follows
    |   |               |   |       FollowDto.java
    |   |               |   |       
    |   |               |   +---member
    |   |               |   |       AccountUpdateDto.java
    |   |               |   |       CustomUserDetails.java
    |   |               |   |       JoinDto.java
    |   |               |   |       LoginDto.java
    |   |               |   |       PasswordChangeDto.java
    |   |               |   |       ProfileDto.java
    |   |               |   |       SearchDto.java
    |   |               |   |       
    |   |               |   +---oauth2
    |   |               |   |       CustomOAuth2User.java
    |   |               |   |       GoogleResponse.java
    |   |               |   |       OAuth2Response.java
    |   |               |   |       OAuth2UserDto.java
    |   |               |   |       
    |   |               |   \---post
    |   |               |           CommentDTO.java
    |   |               |           PostDTO.java
    |   |               |           PostThumbnailDto.java
    |   |               |           
    |   |               +---entity
    |   |               |   +---chat
    |   |               |   |       ChatRoom.java
    |   |               |   |       MessageEntity.java
    |   |               |   |       
    |   |               |   +---follows
    |   |               |   |       FollowsEntity.java
    |   |               |   |       
    |   |               |   +---member
    |   |               |   |       MemberEntity.java
    |   |               |   |       
    |   |               |   +---oauth2
    |   |               |   |       IdpEntity.java
    |   |               |   |       OAuth2UserEntity.java
    |   |               |   |       
    |   |               |   +---posts
    |   |               |   |       Comments.java
    |   |               |   |       Favorites.java
    |   |               |   |       Likes.java
    |   |               |   |       PostImage.java
    |   |               |   |       Posts.java
    |   |               |   |       
    |   |               |   \---token
    |   |               |           RefreshTokenEntity.java
    |   |               |           
    |   |               +---exception
    |   |               |       DuplicateMemberException.java
    |   |               |       NotFoundMemberException.java
    |   |               |       OAuth2ExceptionHandler.java
    |   |               |       
    |   |               +---handler
    |   |               |       CustomFormSuccessHandler.java
    |   |               |       CustomLogoutFilter.java
    |   |               |       CustomOAuth2SuccessHandler.java
    |   |               |       
    |   |               +---jwt
    |   |               |       JWTFilter.java
    |   |               |       JWTUtil.java
    |   |               |       
    |   |               +---repository
    |   |               |   +---chat
    |   |               |   |       ChatRoomRepository.java
    |   |               |   |       MessageRepository.java
    |   |               |   |       
    |   |               |   +---follows
    |   |               |   |       FollowsRepository.java
    |   |               |   |       
    |   |               |   +---member
    |   |               |   |       MemberRepository.java
    |   |               |   |       
    |   |               |   +---oauth2
    |   |               |   |       IdpRepository.java
    |   |               |   |       OAuth2UserRepository.java
    |   |               |   |       
    |   |               |   +---post
    |   |               |   |       CommentRepository.java
    |   |               |   |       FavoritesRepository.java
    |   |               |   |       LikesRepository.java
    |   |               |   |       PostImageRepository.java
    |   |               |   |       PostRepository.java
    |   |               |   |       
    |   |               |   \---token
    |   |               |           RefreshRepository.java
    |   |               |           
    |   |               +---service
    |   |               |   +---chat
    |   |               |   |       ChatRoomService.java
    |   |               |   |       MessageService.java
    |   |               |   |       
    |   |               |   +---follows
    |   |               |   |       FollowService.java
    |   |               |   |       
    |   |               |   +---mail
    |   |               |   |       MailService.java
    |   |               |   |       
    |   |               |   +---member
    |   |               |   |       AccountService.java
    |   |               |   |       CustomUserDetailsService.java
    |   |               |   |       JoinService.java
    |   |               |   |       MemberService.java
    |   |               |   |       ProfileService.java
    |   |               |   |       
    |   |               |   +---oauth2
    |   |               |   |       CustomOAuth2UserService.java
    |   |               |   |       OAuth2JwtHeaderService.java
    |   |               |   |       OAuth2UserService.java
    |   |               |   |       
    |   |               |   +---post
    |   |               |   |       CommentService.java
    |   |               |   |       FavoritesService.java
    |   |               |   |       FileStorageService.java
    |   |               |   |       LikesService.java
    |   |               |   |       PostsService.java
    |   |               |   |       
    |   |               |   \---token
    |   |               |           RefreshTokenService.java
    |   |               |           ReissueService.java
    |   |               |           
    |   |               \---util
    |   |                       CookieUtil.java
    |   |                       SecurityUtil.java
  </code>
</details>

### API 구성

[Swagger API Documentation](http://3.37.125.32:8080/swagger-ui/index.html#/): 닫힌 상태

<details>
  <summary>Swagger API Documentation(클릭해 열기)</summary>
  <br>
  <div align="center">

  ![swagger](https://github.com/user-attachments/assets/b2f9a437-e36d-4cd1-8703-9413dfb84cc1)
  
  </div>
</details>

<div align="right">
  <a href="#목차">맨 위로</a>
</div>

***

## 개발 과정

IA 작성을 통해 구현할 기능 가시화  
<details>
  <summary>Information Architecture(클릭해 열기)</summary>
  <br>
    
  ![InstagramClone_IA](https://github.com/user-attachments/assets/ff9ad436-d483-4e06-a700-718efd564a08)
    
</details>

작성된 IA를 통해 구현에 필요한 기술 스택 정리  
<details>
  <summary>Tech Stacks(클릭해 열기)</summary>
  <br>
  
  ![Software_Stacks](https://github.com/user-attachments/assets/fcccd26d-232e-4797-9d52-61f1bea44f85)
  
</details>

1주에 한번씩 코드 리뷰 및 피드백 진행  
각자 학습할 영역 배정 후 개인별 학습, 코드 작성  
진행하면서 공유해야 할 사항은 메신저를 통해 즉시 공유  
<details>
  <summary>Communication(클릭해 열기)</summary>
  <br>
  
  ![Discord_1](https://github.com/user-attachments/assets/d66d170b-c28d-4b5d-ba18-2d0f0e4aa89e)

  ![Discord_2](https://github.com/user-attachments/assets/74c82324-7bdf-4b42-90e3-96cb353139e1)

  ![Kakao_1](https://github.com/user-attachments/assets/4b919f07-09d8-4065-b1b7-acb315e3f36c)

</details>

<div align="right">
  <a href="#목차">맨 위로</a>
</div>

***

## 이슈 및 트러블슈팅

1. 프론트엔드 엔지니어의 부재
   
   FE를 담당할 인원을 배정하려고 했으나 해당 인원의 일정 문제로 인해 BE 두명으로 진행하게 되었습니다.  
   FE에 대한 짧은 지식으로 페이지를 디자인하려니 보기에는 좀 안쓰러워지는 결과물이 된 것 같습니다.

2. JWT 적용에 많은 어려움을 겪음

   JWT는 대락적으로 알고 있었지만 Token의 분리(Access + Refresh),
   Form 기반의 회원/OAuth2 기반의 회원에 대한 동시 적용,  
   Redis와 Cookie 등 안전하게 토큰을 활용하는 방법 등을 연구하며 많이 코드가 엎어졌습니다.  
   JWT에 대해서는 따로 정리하여 완벽히 이해하는 것이 필요했습니다.

3. 유지보수 및 관리 어려움

   호기롭게 박치기공룡처럼 개발을 시작했으나, 초반에 결정하지 않았던 사항들로 애로사항이 있었습니다.
   버전 관리에서의 어려움, 주먹구구식 배포 과정, 정립되지 않은 에러 핸들러와 API 등
   개발에 있어서 Convention 및 CI/CD 등의 중요성을 깨닫게 되는 시행착오였습니다.

<div align="right">
  <a href="#목차">맨 위로</a>
</div>

***

## 리팩토링 계획

1. 프론트엔드 리팩토링 필요

   당분간 취업 및 공부로 인하여 여유가 없겠지만, 풀스택 개발자로 성장하기 위해 프론트를 공부하게 될 경우 이 프로젝트부터 시작해보려고 합니다.

2. Coding Convention 및 CI/CD

   추가적으로 기능을 개발하게 될 경우 편의성을 위해 틀을 다시 잡고 진행할 예정입니다.  

<div align="right">
  <a href="#목차">맨 위로</a>
</div>

***

## References

#### Spring Boot JWT Tutorial
https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt/dashboard

#### Instagram Clone Project
https://github.com/Instagram-Clone-Coding#%EC%8B%9C%EC%97%B0-%EC%98%81%EC%83%81

<div align="right">
  <a href="#목차">맨 위로</a>
</div>
