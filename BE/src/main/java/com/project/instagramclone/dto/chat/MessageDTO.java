package com.project.instagramclone.dto.chat;

import lombok.*;

@Getter
@Setter
@Builder

@AllArgsConstructor
@NoArgsConstructor

public class MessageDTO {
    private String roomId;
    private String sender;
    private String content;
}
