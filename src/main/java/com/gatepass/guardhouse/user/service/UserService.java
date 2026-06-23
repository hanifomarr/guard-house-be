package com.gatepass.guardhouse.user.service;

import com.gatepass.guardhouse.common.exception.BusinessException;
import com.gatepass.guardhouse.common.exception.ErrorCode;
import com.gatepass.guardhouse.user.dto.CreateUserRequest;
import com.gatepass.guardhouse.user.dto.UserResponse;
import com.gatepass.guardhouse.user.model.User;
import com.gatepass.guardhouse.user.model.UserStatus;
import com.gatepass.guardhouse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse createUser(CreateUserRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.USER_ALREADY_EXISTS);
        }
        User savedUser = userRepository.save(toEntity(request));
        return UserResponse.from(savedUser);
    }

    public UserResponse getUserById(String id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return UserResponse.from(user);
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserResponse::from)
                .toList();
    }

    public UserResponse updateUserStatus(String id, UserStatus status) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.setStatus(status);
        User savedUser = userRepository.save(user);

        return UserResponse.from(savedUser);

    }

    private User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserRole(request.getUserRole());
        user.setUnitNumber(request.getUnitNumber());
        user.setStatus(UserStatus.ACTIVE);
        return user;
    }

}
