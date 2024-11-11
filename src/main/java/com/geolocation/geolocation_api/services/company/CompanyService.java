package com.geolocation.geolocation_api.services.company;

import com.geolocation.geolocation_api.entities.Company;
import com.geolocation.geolocation_api.entities.User;
import com.geolocation.geolocation_api.entities.enums.ERole;
import com.geolocation.geolocation_api.repository.CompanyRepository;
import com.geolocation.geolocation_api.repository.UserRepository;
import com.geolocation.geolocation_api.requests.company.CompanyCreatedRequest;
import com.geolocation.geolocation_api.requests.company.CompanyupdatedRequest;
import com.geolocation.geolocation_api.responses.company.CompanyCreatedResponse;
import com.geolocation.geolocation_api.responses.company.CompanyResponse;
import com.geolocation.geolocation_api.services.utils.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyMapper mapper ;


    public CompanyCreatedResponse createCompany(CompanyCreatedRequest request){
        // Build the company object
        Company company = Company
                .builder()
                .name(request.nameCompany())
                .address(request.address())
                .phone(request.phone())
                .build();

        // Save the company first
        company = companyRepository.save(company);

        // Build the admin user and associate it with the company
        User admin = User
                .builder()
                .firstName(request.adminFirstName())
                .lastName(request.adminLastName())
                .email(request.adminEmail())
                .role(ERole.ROLE_COMPANY_ADMIN)
                .company(company)  // Set the company reference after saving
                .build();

        // Save the admin user
        admin = userRepository.save(admin);

        // Set the admin in the company and save the company again if necessary
        company.setAdmin(admin);
        company = companyRepository.save(company);

        // Return the response
        return mapper.toCompanyCreatedResponse(company);
    }

    public List<CompanyResponse> getAllCompanies(){
        List<Company> companies = companyRepository.findAll();
        List<CompanyResponse> companyCreatedResponses = new ArrayList<>();
        for (Company company : companies) {
            companyCreatedResponses.add(mapper.toCompanyResponse(company));
        }
        return companyCreatedResponses;
    }

    public CompanyResponse getCompanyById(Long id){
        Company company =  companyRepository.findById(id).orElse(null);
        if (company == null){
            return null;
        }
        return mapper.toCompanyResponse(company);
    }

    public CompanyResponse updateCompany(Long id, CompanyupdatedRequest request){
        Company company = companyRepository.findById(id).orElse(null);
        if (company == null){
            return null;
        }
        company.setName(request.nameCompany());
        company.setAddress(request.address());
        company.setPhone(request.phone());
        company = companyRepository.save(company);

        User user = User
                .builder()
                .id(request.adminId())
                .firstName(request.adminFirstName())
                .lastName(request.adminLastName())
                .email(request.adminEmail())
                .role(company.getAdmin().getRole())
                .company(company)
                .build();
        if(user != company.getAdmin()){
            user.setFirstName(request.adminFirstName());
            user.setLastName(request.adminLastName());
            user.setEmail(request.adminEmail());
            user = userRepository.save(user);
        }


        return mapper.toCompanyResponse(company);

    }

    public Long deleteCompany(Long id){
        companyRepository.deleteById(id);
        return id ; // return the id of the deleted company
    }

    public List<CompanyResponse>  getCompaniesBasicInfo() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyResponse> companyResponses = new ArrayList<>();
        companies.forEach(company->{
            companyResponses.add(CompanyResponse
                    .builder()
                    .id(company.getId())
                    .nameCompany(company.getName())
                    .address(company.getAddress())
                    .phone(company.getPhone())
                    .build());
        });
        return companyResponses;
    }
}
