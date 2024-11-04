package com.project.instagramclone.service.chat;

import com.project.instagramclone.entity.chat.Message;
import com.project.instagramclone.repository.chat.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public Message sendMessage(String roomId, String sender, String content) {
        Message message = new Message(roomId, sender, content, LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByRoomId(String roomId) {
        return messageRepository.findByRoomId(roomId);
    }
}