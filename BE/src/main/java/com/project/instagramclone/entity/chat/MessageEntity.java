package com.project.instagramclone.entity.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")
@Getter
@Setter
@NoArgsConstructor
public class MessageEntity {
    @Id
    private String id;
    private String roomId; // DMRoom의 ID
    private String sender; // 발신자 ID
    private String content;
    private LocalDateTime timestamp;

    public MessageEntity(String roomId, String sender, String content, LocalDateTime timestamp) {
        this.roomId = roomId;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }
}
