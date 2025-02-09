package com.example.chat.dto;

import com.example.chat.domain.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {
    private final Long id;
    private final String email;
    private final String nickname;
    private final User.UserStatus status;
    private final LocalDateTime lastLoginAt;
    private final LocalDateTime createdAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.status = user.getStatus();
        this.lastLoginAt = user.getLastLoginAt();
        this.createdAt = user.getCreatedAt();
    }
}