package com.geolocation.geolocation_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CustomerTransactionDTO {
    private UUID id ;
    private String status;
    private String email;
    private String clientName ;
    private Long clientId ;
    private double amount;

    @Override
    public String toString() {
        return "CustomerTransactionDTO{" +
                "status='" + status + '\'' +
                ", email='" + email + '\'' +
                ", amount=" + amount +
                '}';
    }


}