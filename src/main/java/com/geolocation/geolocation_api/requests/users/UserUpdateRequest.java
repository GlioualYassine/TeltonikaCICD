package com.geolocation.geolocation_api.requests.users;

import com.geolocation.geolocation_api.entities.enums.ERole;

public record UserUpdateRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        ERole role,
        Long companyId
) {
}
