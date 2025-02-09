package com.example.chat.controller;

import com.example.chat.domain.ChatRoom;
import com.example.chat.dto.ChatRoomResponse;
import com.example.chat.dto.CreateRoomRequest;
import com.example.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat/rooms")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ChatRoomResponse> createRoom(@RequestBody CreateRoomRequest request) {
        ChatRoom room = chatRoomService.createRoom(request);
        return ResponseEntity.ok(new ChatRoomResponse(room));
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomResponse>> getRooms() {
        return ResponseEntity.ok(chatRoomService.getAllRooms());
    }

    @GetMapping
    @RequestMapping("/{roomId}")
    public ResponseEntity<ChatRoomResponse> getRoom(@PathVariable String roomId) {
        return ResponseEntity.ok(chatRoomService.getRoom(roomId));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ChatRoomResponse>> getUserRooms(@PathVariable String userId) {
        return ResponseEntity.ok(chatRoomService.getRoomsByUserId(userId));
    }
}