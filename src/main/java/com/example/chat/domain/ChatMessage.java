package com.example.chat.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat_messages")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    private String id;
    private String roomId;
    private String senderId;
    private String content;
    private MessageType type;
    private LocalDateTime createdAt;

    public enum MessageType {
        ENTER, TALK, LEAVE
    }
}
