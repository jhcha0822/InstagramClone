package com.project.instagramclone.controller.chat;

import com.project.instagramclone.dto.chat.MessageDTO;
import com.project.instagramclone.entity.chat.MessageEntity;
import com.project.instagramclone.service.chat.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sqs.model.Message;

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
    public List<MessageEntity> getMessagesByRoomId(@PathVariable String roomId) {
        return messageService.getMessagesByRoomId(roomId);
    }

//    private final MessageService messageService;
//
//    @PostMapping("/messages/send")
//    public ResponseEntity<String> sendMessage(@RequestBody MessageDTO messageDTO) {
//        try {
//            String messageBody = String.format("RoomId: %s, Sender: %s, Content: %s",
//                    messageDTO.getRoomId(), messageDTO.getSender(), messageDTO.getContent());
//            messageService.sendMessageToQueue(messageBody);
//            return ResponseEntity.ok("Message sent successfully to SQS!");
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error sending message: " + e.getMessage());
//        }
//    }
//
//    // 특정 채팅방의 모든 메시지 가져오기 (HTTP GET 방식)
//    @GetMapping("/messages/{roomId}")
//    public ResponseEntity<List<MessageEntity>> getMessagesByRoomId(@PathVariable String roomId) {
//        List<MessageEntity> messageEntities = messageService.getMessagesByRoomId(roomId);
//        if (messageEntities.isEmpty()) {
//            return ResponseEntity.notFound().build(); // 메시지가 없으면 404 반환
//        }
//        return ResponseEntity.ok(messageEntities);
//    }
//
//    // SQS 메시지 수신 API
//    @GetMapping("/messages/receive")
//    public ResponseEntity<List<String>> receiveMessages() {
//        List<Message> messages = messageService.receiveMessages();
//        List<String> messageContents = messages.stream()
//                .map(Message::body) // 메시지 본문만 반환
//                .toList();
//
//        // 수신한 메시지 삭제
//        messages.forEach(message -> messageService.deleteMessage(message.receiptHandle()));
//
//        return ResponseEntity.ok(messageContents);
//    }
}
