package com.project.instagramclone.repository.chat;

import com.project.instagramclone.entity.chat.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<MessageEntity, String> {
    List<MessageEntity> findByRoomId(String roomId);
}