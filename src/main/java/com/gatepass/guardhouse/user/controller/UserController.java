package com.gatepass.guardhouse.user.controller;

import com.gatepass.guardhouse.common.response.ApiResponse;
import com.gatepass.guardhouse.user.dto.CreateUserRequest;
import com.gatepass.guardhouse.user.dto.UserResponse;
import com.gatepass.guardhouse.user.model.UserStatus;
import com.gatepass.guardhouse.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(
            @RequestBody @Valid CreateUserRequest request) {

        UserResponse newUser = userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("User created successfully", newUser));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("Users retrieved successfully", users));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<UserResponse>> updateUserStatus(
            @PathVariable String id,
            @RequestBody UserStatus status) {

        UserResponse updatedUser = userService.updateUserStatus(id, status);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success("User status updated successfully", updatedUser));

    }
}
