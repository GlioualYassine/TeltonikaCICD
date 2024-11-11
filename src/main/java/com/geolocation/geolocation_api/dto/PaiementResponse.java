package com.geolocation.geolocation_api.dto;

import lombok.Builder;

import java.util.Date;
import java.util.List;
import java.util.UUID;


public record PaiementResponse(
        UUID id,
        Long clientId,
        String clientName ,
        Date datePaiement,
        Date dateFrom,
        Date dateTo,
        double subtotal,
        double discount,
        double shippingFee,
        double adjustment,
        double total,
        boolean isPaid,
        List<PaymentLineResponse> paymentLines
) {
}

