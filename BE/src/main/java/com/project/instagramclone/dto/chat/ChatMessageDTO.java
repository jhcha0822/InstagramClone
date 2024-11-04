package com.project.instagramclone.dto.chat;

import lombok.*;

@Getter
@Setter
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private String roomId;
    private String sender;  // 발신자 ID
    private String content; // 메시지 내용
}
