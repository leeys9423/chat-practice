package com.example.chat.service;

import com.example.chat.domain.ChatRoom;
import com.example.chat.domain.User;
import com.example.chat.dto.ChatRoomResponse;
import com.example.chat.dto.CreateRoomRequest;
import com.example.chat.repository.ChatRoomRepository;
import com.example.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public ChatRoom createRoom(CreateRoomRequest request) {
        // 참여자 ID 유효성 검증
        List<Long> userIds = request.getParticipantIds();  // Long 타입의 userId로 변경
        validateParticipants(userIds);

        ChatRoom room = ChatRoom.builder()
                .name(request.getName())
                .type(request.getType())
                .participants(request.getParticipantIds())
                .createdAt(LocalDateTime.now())
                .build();

        return chatRoomRepository.save(room);
    }

    private void validateParticipants(List<Long> userIds) {
        // 모든 사용자가 실제로 존재하는지 확인
        List<User> users = userRepository.findAllById(userIds);
        if (users.size() != userIds.size()) {
            throw new IllegalArgumentException("Invalid user ID included in participants");
        }
    }

    public List<ChatRoomResponse> getAllRooms() {
        return chatRoomRepository.findAll().stream()
                .map(ChatRoomResponse::new)
                .toList();
    }

    public ChatRoomResponse getRoom(String roomId) {
        return new ChatRoomResponse(chatRoomRepository.findById(roomId)
                                        .orElseThrow(() -> new RuntimeException("검색한 방이 없습니다.")));
    }

    public List<ChatRoomResponse> getRoomsByUserId(String userId) {
        return chatRoomRepository.findByParticipantsContaining(userId).stream()
                .map(ChatRoomResponse::new)
                .toList();
    }
}