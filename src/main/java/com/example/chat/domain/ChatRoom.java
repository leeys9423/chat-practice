package com.example.chat.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "chat_rooms")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    private String id;
    private String name;
    private RoomType type;
    private List<Long> participants;
    private LocalDateTime createdAt;

    public enum RoomType {
        GROUP, PRIVATE
    }

    public void addParticipant(Long userId) {
        if (this.participants == null) {
            this.participants = new ArrayList<>();
        }
        if (!this.participants.contains(userId)) {
            this.participants.add(userId);
        }
    }

    public void removeParticipant(Long userId) {
        if (this.participants != null) {
            this.participants.remove(userId);
        }
    }
}
