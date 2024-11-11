package com.geolocation.geolocation_api.dto;

import java.util.Date;
import java.util.List;


public record PaiementRequest(
        Long clientId,
        List<DevicePaymentInfo> devices, // List of devices with price and TVA details
        double subtotal,
        double discount,
        double shippingFee,
        double adjustment,
        Date dateFrom,
        Date dateTo,
        Date datePaiement,
        boolean paid
) {

    @Override
    public String toString() {
        return "is paid: " + paid + ", date of payment: " + datePaiement;
    }
}
