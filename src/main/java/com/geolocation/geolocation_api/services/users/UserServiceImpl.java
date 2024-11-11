package com.geolocation.geolocation_api.services.users;

import com.geolocation.geolocation_api.entities.Company;
import com.geolocation.geolocation_api.entities.User;
import com.geolocation.geolocation_api.entities.enums.ERole;
import com.geolocation.geolocation_api.exceptions.user.EmailAlreadyExistsException;
import com.geolocation.geolocation_api.repository.CompanyRepository;
import com.geolocation.geolocation_api.repository.UserRepository;
import com.geolocation.geolocation_api.requests.users.UserRequest;
import com.geolocation.geolocation_api.requests.users.UserUpdateRequest;
import com.geolocation.geolocation_api.responses.auth.AuthonicationResponse;
import com.geolocation.geolocation_api.responses.users.UserResponse;
import com.geolocation.geolocation_api.security.filter.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements com.geolocation.geolocation_api.services.interfaces.IUserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CompanyRepository companyRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        return  userRepository.save(user);
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request  , Long id) {

        User user1 = userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("User not found"));
        if(request.companyId() != null){
            user1.setCompany(companyRepository.findById(request.companyId()).orElse(null));
        }
        user1.setFirstName(request.firstName());
        user1.setLastName(request.lastName());
        user1.setEmail(request.email());
        if(request.password() != null){
            user1.setPassword(request.password());
        }
        user1.setRole(request.role());
        user1 =  userRepository.save(user1);

        return UserResponse
                .builder()
                .firstName(user1.getFirstName())
                .lastName(user1.getLastName())
                .email(user1.getEmail())
                .role(user1.getRole())
                .companyId(user1.getCompany() == null ? null : user1.getCompany().getId())
                .companyName(user1.getCompany() == null ? null : user1.getCompany().getName())
                .id(user1.getId())
                .build();
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            System.out.println("User not found with id: " + id);
            return;
        }

        System.out.println("Deleting user: " + user);

        // Retirer l'utilisateur de toutes les entreprises
        List<Company> companies = companyRepository.findAll();
        for (Company company : companies) {
            if (company.getUsers().contains(user)) {
                System.out.println("Removing user from company: " + company.getName());
                System.out.println(company.getUsers().remove(user));
                var c = companyRepository.save(company);
                System.out.println("Company after removing user: " + c);
            }
        }

        // Supprimer l'utilisateur
        //userRepository.deleteById(id);
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user =  userRepository.findById(id).
                orElseThrow(() -> new RuntimeException("User not found"));
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .companyId(user.getCompany() == null ? null : user.getCompany().getId())
                .companyName(user.getCompany() == null ? null : user.getCompany().getName())
                .build();

        return userResponse;
    }

    @Override
    public List<UserResponse> getClients() {
        List<User> clients = userRepository.findByRole(ERole.ROLE_USER);
        if(clients != null && !clients.isEmpty()){
            return clients.stream().map(client -> {
                UserResponse userResponse = new UserResponse();
                userResponse.setId(client.getId());
                userResponse.setFirstName(client.getFirstName());
                userResponse.setLastName(client.getLastName());
                userResponse.setEmail(client.getEmail());
                userResponse.setRole(client.getRole());
                userResponse.setCompanyName(client.getCompany() == null ? null :  client.getCompany().getName());
                userResponse.setCompanyId(client.getCompany() == null ? null :   client.getCompany().getId());
                return userResponse;
            }).toList();
        }
        return List.of();
    }

    @Override
    public Map<String,Object> createClient(UserRequest userRequest) {
       // Create a new User object and set its properties from the UserRequest object
        System.out.println("req  "+userRequest);
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(ERole.ROLE_USER);
        Company company = null ;
        if (userRequest.getCompanyId() != null) {
            company = companyRepository.findById(userRequest.getCompanyId()).orElse(null);
            user.setCompany(company);
        }
        // Save the new User object to the repository and get the created User object
        User createdUser = userRepository.save(user);

        // Create a new UserResponse object and set its properties from the created User object
        UserResponse userResponse = new UserResponse();
        userResponse.setFirstName(createdUser.getFirstName());
        userResponse.setId(createdUser.getId());
        userResponse.setLastName(createdUser.getLastName());
        userResponse.setEmail(createdUser.getEmail());
        userResponse.setRole(createdUser.getRole());
        if(company != null){
            userResponse.setCompanyId(company.getId());
            userResponse.setCompanyName(company.getName());
        }
        // Generate a JWT token for the new user
        var jwtToken = jwtService.generateToken(user);

        // Create a response map containing the JWT token and the UserResponse object
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwtToken);
        response.put("user", userResponse);

        // Return the response map
        return response;
    }


}
