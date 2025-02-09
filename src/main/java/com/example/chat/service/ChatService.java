package com.example.chat.service;

import com.example.chat.domain.ChatMessage;
import com.example.chat.domain.ChatRoom;
import com.example.chat.dto.ChatMessageResponse;
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

    public void sendMessage(ChatMessage message) {
        // 1. 메시지 저장
        ChatMessage savedMessage = chatMessageRepository.save(message);

        // 2. 메시지 브로드캐스트
        messagingTemplate.convertAndSend(
                "/topic/chat." + message.getRoomId(),
                new ChatMessageResponse(savedMessage)
        );
    }

    public List<ChatMessageResponse> getRoomMessages(String roomId) {
        return chatMessageRepository.findByRoomIdOrderByCreatedAtDesc(roomId).stream()
                .map(ChatMessageResponse::new)
                .toList();
    }

    public Optional<ChatRoom> getRoom(String roomId) {
        return chatRoomRepository.findById(roomId);
    }
}
