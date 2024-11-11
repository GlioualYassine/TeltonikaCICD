package com.geolocation.geolocation_api.responses.company;

import com.geolocation.geolocation_api.responses.users.UserResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record CompanyCreatedResponse (
        Long id,
        String nameCompany ,
        String address ,
        String phone ,
        UserResponse admin,
        List<UserResponse> users
){
}
