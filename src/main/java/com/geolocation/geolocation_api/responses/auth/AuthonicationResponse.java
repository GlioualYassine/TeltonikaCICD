package com.geolocation.geolocation_api.responses.auth;

import com.geolocation.geolocation_api.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthonicationResponse {
    private String token;
    private User user;
}
