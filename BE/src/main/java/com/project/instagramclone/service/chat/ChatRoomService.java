package com.project.instagramclone.service.chat;

import com.project.instagramclone.entity.chat.ChatRoom;
import com.project.instagramclone.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    // 중복 채팅방 확인 메서드
    public ChatRoom findExistingChatRoom(List<String> users) {
        List<ChatRoom> allRooms = chatRoomRepository.findAll();
        for (ChatRoom room : allRooms) {
            if (room.getUsers().containsAll(users) && users.containsAll(room.getUsers())) {
                return room; // 이미 존재하는 채팅방 반환
            }
        }
        return null; // 중복 채팅방이 없을 경우 null 반환
    }


    public ChatRoom createChatRoom(List<String> users) {
        ChatRoom chatRoom = new ChatRoom(users);
        return chatRoomRepository.save(chatRoom);
    }

    public List<ChatRoom> getUserChatRooms(String userId) {
        return chatRoomRepository.findByUsersContaining(userId);
    }
}