package com.geolocation.geolocation_api.services.interfaces;

import com.geolocation.geolocation_api.entities.User;
import com.geolocation.geolocation_api.requests.users.UserRequest;
import com.geolocation.geolocation_api.requests.users.UserUpdateRequest;
import com.geolocation.geolocation_api.responses.users.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public interface IUserService {

    List<User> getUsers();

    User createUser(User userDto);

    UserResponse updateUser(UserUpdateRequest request , Long id);

    void deleteUser(Long id);

    UserResponse getUserById(Long id);

    List<UserResponse> getClients();

    Map<String,Object> createClient(UserRequest userRequest);
}
