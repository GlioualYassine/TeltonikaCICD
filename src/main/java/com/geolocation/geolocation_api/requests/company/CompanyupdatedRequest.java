package com.geolocation.geolocation_api.requests.company;

import com.geolocation.geolocation_api.entities.enums.ERole;

public record CompanyupdatedRequest(
        String nameCompany ,
        String address ,
        String phone ,
        Long adminId ,
        String adminFirstName ,
        String adminLastName ,
        String adminEmail,
        ERole adminRole
) {
}
