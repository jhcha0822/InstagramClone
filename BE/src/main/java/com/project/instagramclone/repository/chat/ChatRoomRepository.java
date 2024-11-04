package com.project.instagramclone.repository.chat;

import com.project.instagramclone.entity.chat.ChatRoom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {
    List<ChatRoom> findByUsersContaining(String userId);
}