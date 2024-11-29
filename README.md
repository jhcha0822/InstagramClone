# InstagramClone: 인스타그램 클론 프로젝트

## 프로젝트 개요

**InstagramClone**은 Spring Boot와 React를 사용하여 Instagram과 유사한 소셜 미디어 플랫폼을 구현한 클론 코딩 프로젝트입니다.  
주요 기능은 회원가입, 로그인, 게시물 작성/수정/삭제, 팔로우, DM 등이 포함됩니다.

***

## 프로젝트 목적

This project was created to solidify our understanding of Spring Boot, explore other services, and complete the full development-to-deployment cycle.

Spring Boot에 대해 학습하며 이를 숙달하기 위해 프로젝트를 진행하였습니다.  
적용해보고 싶은 서비스들을 추가하였고, 이를 배포까지 해보는 과정을 통해 현업의 개발-배포 사이클을 학습하였습니다.

***

## 개발 기간 및 인원

2024.08 ~ 2024.11 (4개월)

차정현[BE] jhcha0822@gmail.com <br>
Authentication, Security, Mailing, Deployment

윤성진[BE] ckwdktek12@gmail.com <br>
Post, Comments, Messaging, Deployment

***

## 목차
1. []()
2. []()
3. []()
4. []()
5. []()
6. []()
7. []()
8. []()

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

***

## 프로젝트 구조

### 디렉토리 구조

### API 구성

[Swagger API Documentation](http://3.37.125.32:8080/swagger-ui/index.html#/): 닫힌 상태

<details>
  <summary>Swagger API Documentation(클릭해 열기)</summary>
  <br>
  <div align="center">

  ![swagger](https://github.com/user-attachments/assets/b2f9a437-e36d-4cd1-8703-9413dfb84cc1)
  
  </div>
</details>

***

## 개발 과정

***

## 이슈 및 트러블슈팅

***

## 리팩토링 계획

***

## References

#### Spring Boot JWT Tutorial
https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-jwt/dashboard
