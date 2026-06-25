package com.gatepass.guardhouse.auth.service;

import com.gatepass.guardhouse.auth.dto.AuthResponse;
import com.gatepass.guardhouse.auth.dto.LoginRequest;
import com.gatepass.guardhouse.auth.security.GuardhouseUserDetails;
import com.gatepass.guardhouse.auth.security.JwtUtil;
import com.gatepass.guardhouse.common.exception.BusinessException;
import com.gatepass.guardhouse.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    public AuthResponse login(LoginRequest request) {

        GuardhouseUserDetails userDetails = (GuardhouseUserDetails) userDetailsService.loadUserByUsername(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        String userId = userDetails.getUserId();

        String role = userDetails.getAuthorities()
                .iterator().next()
                .getAuthority();

        String token = jwtUtil.generateToken(userDetails.getUsername(), role, userId);

        return new AuthResponse(token, "Bearer", expirationMs);
    }
}
