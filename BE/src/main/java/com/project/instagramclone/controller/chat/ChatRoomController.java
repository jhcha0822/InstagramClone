package com.project.instagramclone.controller.chat;

import com.project.instagramclone.entity.chat.ChatRoom;
import com.project.instagramclone.jwt.JWTUtil;
import com.project.instagramclone.service.chat.ChatRoomService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final JWTUtil jwtUtil; //

    @PostMapping("/rooms")
    public ResponseEntity<?> createChatRoom(@RequestBody List<String> users, HttpServletRequest request) {
        String nickname = extractUserIdFromToken(request); // JWT에서 닉네임 추출
        users.add(nickname); // 현재 유저의 닉네임 추가

        // 중복 채팅방 확인 후 생성
        ChatRoom existingRoom = chatRoomService.findExistingChatRoom(users);
        if (existingRoom != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 채팅방이 생성되어 있습니다.");
        }

        // 중복 채팅방이 없으면 새로 생성
        ChatRoom newChatRoom = chatRoomService.createChatRoom(users);
        return ResponseEntity.ok(newChatRoom);
    }
    @GetMapping("/rooms")
    public List<ChatRoom> getUserChatRooms(HttpServletRequest request) {
        String nickname = extractUserIdFromToken(request); // JWT에서 닉네임 추출
        return chatRoomService.getUserChatRooms(nickname);
    }

    private String extractUserIdFromToken(HttpServletRequest request) {
        // Authorization 헤더에서 토큰 가져오기
        String token = request.getHeader("Authorization");

        // 토큰에서 Bearer 부분을 제거
        String actualToken = token.replace("Bearer ", "");

        // 토큰에서 닉네임 추출 후 리턴
        return jwtUtil.getNickname(actualToken);
    }
}