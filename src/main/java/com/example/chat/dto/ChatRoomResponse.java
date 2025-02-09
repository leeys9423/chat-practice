package com.example.chat.dto;

import com.example.chat.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ChatRoomResponse {
    private String id;
    private String name;
    private ChatRoom.RoomType type;
    private List<Long> participants;
    private LocalDateTime createdAt;

    public ChatRoomResponse(ChatRoom room) {
        this.id = room.getId();
        this.name = room.getName();
        this.type = room.getType();
        this.participants = room.getParticipants();
        this.createdAt = room.getCreatedAt();
    }
}