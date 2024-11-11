package com.geolocation.geolocation_api.dto;

public record PaymentLineResponse(
        Long id,
        String imei,
        double unitPrice,
        double tva,
        double totalPrice
) {}
