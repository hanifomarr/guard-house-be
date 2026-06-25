package com.gatepass.guardhouse.auth.service;

import com.gatepass.guardhouse.auth.security.GuardhouseUserDetails;
import com.gatepass.guardhouse.user.model.User;
import com.gatepass.guardhouse.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Authentication failed"));
        return new GuardhouseUserDetails(user);

    }
}
