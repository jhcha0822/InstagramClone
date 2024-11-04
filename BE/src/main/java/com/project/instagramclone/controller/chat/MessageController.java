package com.project.instagramclone.controller.chat;

import com.project.instagramclone.dto.chat.MessageDTO;
import com.project.instagramclone.entity.chat.Message;
import com.project.instagramclone.service.chat.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate; // 메시징 템플릿 추가

    // WebSocket 메시지 전송 메서드
    @MessageMapping("/chat")
    public void handleMessage(MessageDTO message) {
        String roomId = message.getRoomId();
        System.out.println("roomId =" + roomId);
        System.out.println("sender =" + message.getSender());
        System.out.println("content =" + message.getContent());

        // 메시지 DB에 저장
        messageService.sendMessage(roomId, message.getSender(), message.getContent());

        // 클라이언트에게 메시지 전송
        messagingTemplate.convertAndSend("/topic/messages/" + roomId, message);
    }

    // 특정 채팅방의 모든 메시지 가져오기 (HTTP GET 방식)
    @GetMapping("/messages/{roomId}")
    public List<Message> getMessagesByRoomId(@PathVariable String roomId) {
        return messageService.getMessagesByRoomId(roomId);
    }
}
