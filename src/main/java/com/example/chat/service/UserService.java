package com.example.chat.service;

import com.example.chat.domain.User;
import com.example.chat.dto.CreateUserRequest;
import com.example.chat.dto.UpdateUserRequest;
import com.example.chat.dto.UserResponse;
import com.example.chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        validateDuplicateEmail(request.getEmail());

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .nickname(request.getNickname())
                .status(User.UserStatus.OFFLINE)
                .build();

        return new UserResponse(userRepository.save(user));
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::new)
                .toList();
    }

    @Transactional
    public UserResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 이메일 변경 시 중복 체크
        if (!user.getEmail().equals(request.getEmail())) {
            validateDuplicateEmail(request.getEmail());
        }

        user.update(
                request.getNickname(),
                request.getEmail(),
                request.getPassword() != null ?
                        request.getPassword() : user.getPassword()
        );

        return new UserResponse(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
    }
}
