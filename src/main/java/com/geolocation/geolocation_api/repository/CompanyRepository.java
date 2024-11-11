package com.geolocation.geolocation_api.repository;


import com.geolocation.geolocation_api.entities.Company;
import com.geolocation.geolocation_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByName(String name);
    Company findByAdmin(User admin);
    Company findByUsers(User user);
    Company findByAddress(String address);
    Company findByPhone(String phone);
    Company findByUsersContains(User user);

}
