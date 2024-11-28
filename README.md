# InstagramClone: 인스타그램 클론 프로젝트

## 프로젝트 소개

**InstagramClone**은 Spring Boot와 React를 기반으로 소셜 미디어 서비스 **인스타그램**의 서비스를 구현해보는 프로젝트입니다.

***

## 프로젝트 목적

This project was created to solidify our understanding of Spring Boot, explore other services, and complete the full development-to-deployment cycle.

Spring Boot에 대해 학습하며 이를 숙달하기 위해 프로젝트를 진행하였습니다. 적용해보고 싶은 서비스들을 추가하였고, 이를 배포까지 해보는 과정을 통해 현업의 개발-배포 사이클을 학습하였습니다.

***

## 개발 기간 및 인원

2024.08 ~ 2024.11 (4개월)

차정현[BE] jhcha0822@gmail.com <br>
Authentication, Security, Mailing, Deployment

윤성진[BE] ckwdktek12@gmail.com <br>
Post, Comments, Messaging, Deployment

챗지피티[FE] https://chatgpt.com/ <br>
Designing, Debugging, Supervising

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

- 사용자 인증 (ID/PW, OAuth2)
- 메일 전송
- 사용자 정보 변경
- 프로필 검색, 조회
- 팔로우
- 게시글
  - 좋아요, 즐겨찾기
  - 댓글, 대댓글
- DM (Direct Message)

***

## 기능 화면

[배포 사이트](http://3.37.125.32/) : 닫힌 상태

### 시연 이미지/영상

회원가입  
![join](https://github.com/user-attachments/assets/87c25568-e9db-42be-8158-116ff7cf02f6)

로그인(ID/PW 방식)  
![login_form](https://github.com/user-attachments/assets/1762c81f-cc4a-4370-9c6c-38bd1cbe73cb)

팔로우  
![follow](https://github.com/user-attachments/assets/22385142-08c0-4ce8-ad4c-5f9755d0448f)

댓글/대댓글  
![reply](https://github.com/user-attachments/assets/7bb5461c-cd74-403e-b6cf-0b89b7222569)

게시글 작성, 프로필 페이지  
![post_profile](https://github.com/user-attachments/assets/f478d7e0-1108-4c45-baf5-72ab8a8e203b)

즐겨찾기, 좋아요  
![favorites](https://github.com/user-attachments/assets/f7de032f-1153-488a-a333-1fe797e22c55)

DM(채팅)  
![DM](https://github.com/user-attachments/assets/2a5b8961-ac82-49c1-ab5e-4542c000541d)


***

## 기술 스택

### Backend

|Stack|Used|
|:---:|:---:|
|Language|Java 17|
|Framework|Spring Boot 3.3.1| 
|Build|Gradle|
|ORM|JPA 3.1.0|
|SMTP|Gmail|
|Authorization|Spring Security, JWT|
|Messaging|Websocket, STOMP|
|API Documentation|Swagger|
|DataBase|H2, MySQL, MongoDB, Redis|

### Frontend

|Stack|Used|
|:---:|:---:|
|Language|JS|
|Library|React|
|Messaging|SockJS, StompJS|

### Deployment

- EC2 (Linux Server)
- RDS (Relational DB)
- S3 (Image Storage)
- ElastiCache (Redis Cache)
- VPC

***

## 프로젝트 구조

### 디렉토리 구조

### API 구성

[Swagger API Documentation](http://3.37.125.32:8080/swagger-ui/index.html#/): 닫힌 상태

![swagger](https://github.com/user-attachments/assets/b2f9a437-e36d-4cd1-8703-9413dfb84cc1)


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
