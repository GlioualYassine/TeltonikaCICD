package com.geolocation.geolocation_api.controllers.company;

import com.geolocation.geolocation_api.requests.company.CompanyCreatedRequest;
import com.geolocation.geolocation_api.requests.company.CompanyupdatedRequest;
import com.geolocation.geolocation_api.responses.company.CompanyCreatedResponse;
import com.geolocation.geolocation_api.responses.company.CompanyResponse;
import com.geolocation.geolocation_api.services.company.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping()
    public ResponseEntity<CompanyCreatedResponse> createCompany(@RequestBody CompanyCreatedRequest request){
        return ResponseEntity.ok(companyService.createCompany(request));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCompany(@PathVariable Long id, @RequestBody CompanyupdatedRequest request){
        return ResponseEntity.ok(companyService.updateCompany(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id){
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCompany(@PathVariable Long id ){
        return ResponseEntity.ok(companyService.deleteCompany(id));
    }

    @GetMapping("/getCompaniesBasicInfo")
    public ResponseEntity<List<CompanyResponse> > getCompanies(){
        return ResponseEntity.ok(companyService.getCompaniesBasicInfo());
    }

}
