package com.example.chat.dto;

import com.example.chat.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatMessageResponse {
    private String id;
    private String roomId;
    private String senderId;
    private String content;
    private ChatMessage.MessageType type;
    private LocalDateTime createdAt;

    public ChatMessageResponse(ChatMessage message) {
        this.id = message.getId();
        this.roomId = message.getRoomId();
        this.senderId = message.getSenderId();
        this.content = message.getContent();
        this.type = message.getType();
        this.createdAt = message.getCreatedAt();
    }
}
