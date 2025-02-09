package com.example.chat.dto;

import com.example.chat.domain.ChatRoom;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateRoomRequest {
    private String name;
    private ChatRoom.RoomType type;
    private List<Long> participantIds;

    @Builder
    public CreateRoomRequest(String name, ChatRoom.RoomType type, List<Long> participantIds) {
        this.name = name;
        this.type = type;
        this.participantIds = participantIds;
    }
}