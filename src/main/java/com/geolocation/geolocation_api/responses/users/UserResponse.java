package com.geolocation.geolocation_api.responses.users;

import com.geolocation.geolocation_api.entities.Company;
import com.geolocation.geolocation_api.entities.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private ERole role ;
    private String companyName;
    private Long companyId;
}