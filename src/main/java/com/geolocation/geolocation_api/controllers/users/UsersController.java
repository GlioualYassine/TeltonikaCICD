package com.geolocation.geolocation_api.controllers.users;

import com.geolocation.geolocation_api.entities.User;
import com.geolocation.geolocation_api.requests.users.UserRequest;
import com.geolocation.geolocation_api.requests.users.UserUpdateRequest;
import com.geolocation.geolocation_api.responses.users.UserResponse;
import com.geolocation.geolocation_api.services.users.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor

public class UsersController {

    private final UserServiceImpl userService;

    @GetMapping("/users")
    public  ResponseEntity<List<UserResponse>> getUsers() {
        List<User> users = userService.getUsers();

        List<UserResponse> usersRep = users.stream().map(user -> {
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(user, userResponse);
            return userResponse;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(usersRep);
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        User createdUser = userService.createUser(user);
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(createdUser, userResponse);

        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userRequest) {
       return ResponseEntity.ok(userService.updateUser(userRequest, id));
    }

    @DeleteMapping("/users/{id}")
    public boolean deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return true;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    //clients section

    @GetMapping("/users/clients")
    public ResponseEntity<List<UserResponse>> getClients(){
        return ResponseEntity.ok(userService.getClients());
    }

    @PostMapping("/users/clients")
    public ResponseEntity<Map<String,Object>> createClient(@RequestBody @Valid UserRequest userRequest){
        return ResponseEntity.ok(userService.createClient(userRequest));
    }

    @PostMapping("/users/clients/bcp")
    public ResponseEntity<List<Map<String,Object>>> createClient(@RequestBody List<UserRequest> userRequests){
        // List<Map<String,Object>> response = userRequests.stream().map(userRequest -> userService.createClient(userRequest)).collect(Collectors.toList()); OR
        List<Map<String,Object>> response = userRequests.stream().map(userService::createClient).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

}
