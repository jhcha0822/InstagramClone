package com.project.instagramclone.repository.chat;

import com.project.instagramclone.entity.chat.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByRoomId(String roomId);
}