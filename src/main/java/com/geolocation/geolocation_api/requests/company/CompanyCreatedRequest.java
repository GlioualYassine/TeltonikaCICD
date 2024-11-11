package com.geolocation.geolocation_api.requests.company;

public record CompanyCreatedRequest(
        String nameCompany ,
        String address ,
        String phone ,
        String adminFirstName ,
        String adminLastName ,
        String adminEmail
) {
}
