package com.odis.monitoreo.demo.auth.service;

import com.odis.monitoreo.demo.auth.models.AuthResponse;
import com.odis.monitoreo.demo.auth.models.LoginRequest;
import com.odis.monitoreo.demo.auth.models.RegisterRequest;
import com.odis.monitoreo.demo.jwt.JwtService;
import com.odis.monitoreo.demo.user.models.Role;
import com.odis.monitoreo.demo.user.models.User;
import com.odis.monitoreo.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByEmail(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
    public AuthResponse register(RegisterRequest request){
        User user = User.builder()
                .email(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Role.ROLE_USER)
                .build();

        userRepository
                .save(user);

        return AuthResponse
                .builder()
                .token(jwtService.getToken(user))
                .build();
    }

}
