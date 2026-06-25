package com.gatepass.guardhouse.user.dto;

import com.gatepass.guardhouse.user.model.UserRole;
import com.gatepass.guardhouse.user.model.User;
import com.gatepass.guardhouse.user.model.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private String id;
    private String name;
    private String email;
    private UserRole userRole;
    private String unitNumber;
    private UserStatus status;
    private Instant createdAt;

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .userRole(user.getUserRole())
                .unitNumber(user.getUnitNumber())
                .status(user.getStatus())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
