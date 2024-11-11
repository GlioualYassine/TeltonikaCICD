package com.geolocation.geolocation_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class DashboardResponseDTO {
    private Map<String, Double> summary;
    private List<CustomerTransactionDTO> lastCustomers;
}
