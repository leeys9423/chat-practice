package com.example.chat.repository;

import com.example.chat.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    List<ChatMessage> findByRoomId(String roomId);
    List<ChatMessage> findByRoomIdOrderByCreatedAtDesc(String roomId);
}
