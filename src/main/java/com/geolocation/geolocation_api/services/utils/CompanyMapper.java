package com.geolocation.geolocation_api.services.utils;

import com.geolocation.geolocation_api.entities.Company;
import com.geolocation.geolocation_api.responses.company.CompanyCreatedResponse;
import com.geolocation.geolocation_api.responses.company.CompanyResponse;
import com.geolocation.geolocation_api.responses.users.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyMapper {
    public CompanyCreatedResponse toCompanyCreatedResponse(Company company) {

        UserResponse userResponse = UserResponse
                .builder()
                .id(company.getAdmin().getId())
                .firstName(company.getAdmin().getFirstName())
                .lastName(company.getAdmin().getLastName())
                .email(company.getAdmin().getEmail())
                .role(company.getAdmin().getRole())
                .build();
        if(company.getUsers() == null){
            return CompanyCreatedResponse
                    .builder()
                    .id(company.getId())
                    .nameCompany(company.getName())
                    .address(company.getAddress())
                    .phone(company.getPhone())
                    .admin(userResponse)
                    .build();
        }else {
            List<UserResponse> users = company.getUsers().stream().map(user ->
                    UserResponse
                            .builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .build()).toList();

            return CompanyCreatedResponse
                    .builder()
                    .id(company.getId())
                    .nameCompany(company.getName())
                    .address(company.getAddress())
                    .phone(company.getPhone())
                    .admin(userResponse)
                    .users(users)
                    .build();
        }
    }

    //for GetAllCompanies
    public CompanyResponse toCompanyResponse(Company company){

            UserResponse userResponse = UserResponse
                    .builder()
                    .id(company.getAdmin().getId())
                    .firstName(company.getAdmin().getFirstName())
                    .lastName(company.getAdmin().getLastName())
                    .email(company.getAdmin().getEmail())
                    .role(company.getAdmin().getRole())
                    .build();
        if(company.getUsers() == null){
            return CompanyResponse
                    .builder()
                    .id(company.getId())
                    .nameCompany(company.getName())
                    .address(company.getAddress())
                    .phone(company.getPhone())
                    .admin(userResponse)
                    .build();
        }else {
            List<UserResponse> users = company.getUsers().stream().map(user ->
                    UserResponse
                            .builder()
                            .id(user.getId())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .role(user.getRole())
                            .build()).toList();

            return CompanyResponse
                    .builder()
                    .id(company.getId())
                    .nameCompany(company.getName())
                    .address(company.getAddress())
                    .phone(company.getPhone())
                    .admin(userResponse)
                    .users(users)
                    .build();
        }

    }
}
