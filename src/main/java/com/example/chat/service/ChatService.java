package com.example.chat.service;

import com.example.chat.domain.ChatMessage;
import com.example.chat.domain.ChatRoom;
import com.example.chat.repository.ChatMessageRepository;
import com.example.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    public ChatMessage saveAndSend(ChatMessage message) {
        message.setCreatedAt(LocalDateTime.now());
        ChatMessage saved = chatMessageRepository.save(message);

        messagingTemplate.convertAndSend("/topic/chat." + message.getRoomId(), saved);
        return saved;
    }

    public List<ChatMessage> getRoomMessages(String roomId) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtDesc(roomId);
    }

    public Optional<ChatRoom> getRoom(String roomId) {
        return chatRoomRepository.findById(roomId);
    }
}
