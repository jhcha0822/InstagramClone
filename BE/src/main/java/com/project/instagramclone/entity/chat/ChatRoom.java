package com.project.instagramclone.entity.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

// 채팅방 모델
@Document(collection = "chatRooms")
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
    @Id
    private String id;
    private List<String> users; // 참여자 ID 목록 (2명)

    public ChatRoom(List<String> users) {
        this.users = users;
    }
}
