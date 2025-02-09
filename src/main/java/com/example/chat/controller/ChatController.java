package com.example.chat.controller;

import com.example.chat.domain.ChatMessage;
import com.example.chat.dto.ChatMessageResponse;
import com.example.chat.dto.ChatRoomResponse;
import com.example.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chat.message.{roomId}")
    @SendTo("/topic/chat.{roomId}")
    public ChatMessageResponse sendMessage(@DestinationVariable String roomId,
                                           @Payload ChatMessage message) {
        return new ChatMessageResponse(chatService.saveAndSend(message));
    }

    @MessageMapping("/chat.enter.{roomId}")
    @SendTo("/topic/chat.{roomId}")
    public ChatMessageResponse enter(@DestinationVariable String roomId,
                                     @Payload ChatMessage message) {
        message.setType(ChatMessage.MessageType.ENTER);
        return new ChatMessageResponse(chatService.saveAndSend(message));
    }

    // REST API 엔드포인트 추가
    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getRoomMessages(@PathVariable String roomId) {
        return ResponseEntity.ok(chatService.getRoomMessages(roomId));
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<ChatRoomResponse> getRoom(@PathVariable String roomId) {
        return chatService.getRoom(roomId)
                .map(room -> ResponseEntity.ok(new ChatRoomResponse(room)))
                .orElse(ResponseEntity.notFound().build());
    }
}