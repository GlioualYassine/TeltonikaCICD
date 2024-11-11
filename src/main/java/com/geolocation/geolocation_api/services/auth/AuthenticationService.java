package com.geolocation.geolocation_api.services.auth;

import com.geolocation.geolocation_api.entities.User;
import com.geolocation.geolocation_api.entities.enums.ERole;
import com.geolocation.geolocation_api.repository.UserRepository;
import com.geolocation.geolocation_api.requests.auth.LoginRequest;
import com.geolocation.geolocation_api.requests.auth.RegisterRequest;
import com.geolocation.geolocation_api.responses.auth.AuthonicationResponse;
import com.geolocation.geolocation_api.security.filter.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthonicationResponse login(LoginRequest loginRequest) {
       authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        log.info("user : {}",user);
        var jwtToken = jwtService.generateToken(user);
        return AuthonicationResponse.builder()
                .token(jwtToken)
                .user(user)
                .build();

    }

    public AuthonicationResponse register(RegisterRequest registerRequest) {
        var user = User.builder()
                .lastName(registerRequest.getLastName())
                .firstName(registerRequest.getFirstName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(ERole.ROLE_GENERAL_ADMIN)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthonicationResponse.builder()
                .token(jwtToken)
                .user(user)
                .build();
    }

    // Method to get the currently authenticated user
    public User getAuthenticatedUser() {
        // Get the authentication object from the security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if authentication is not null and if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Get the principal (which could be a UserDetails object or your custom User object)
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserDetails) {
                // If the principal is a UserDetails object, you can retrieve the username/email
                String username = ((UserDetails) principal).getUsername();

                // Retrieve and return the user from the repository using the username or email
                return userRepository.findByEmail(username)
                        .orElseThrow(() -> new RuntimeException("User not found"));
            }
        }
        throw new RuntimeException("User is not authenticated");
    }
}
