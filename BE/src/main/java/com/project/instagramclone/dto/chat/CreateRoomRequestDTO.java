package com.project.instagramclone.dto.chat;

import lombok.*;

@Getter
@Setter
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequestDTO {
    private String sender;
    private String receiverNickname;
}