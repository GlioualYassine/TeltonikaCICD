package com.geolocation.geolocation_api.repository;

import com.geolocation.geolocation_api.entities.User;
import com.geolocation.geolocation_api.entities.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByRole(ERole role);

    boolean existsByEmail(String email);
}
