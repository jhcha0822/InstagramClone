package com.project.instagramclone.dto.chat;

import lombok.*;

@Data
@Getter
@Setter
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
    private String sender;
    private String content;
}