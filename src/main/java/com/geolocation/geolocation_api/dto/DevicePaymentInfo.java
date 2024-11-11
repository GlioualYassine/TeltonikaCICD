package com.geolocation.geolocation_api.dto;

public record DevicePaymentInfo(
        String imei,      // The IMEI of the GPS device
        double unitPrice, // The unit price of the device
        double tva        // The VAT (TVA) for this device in percentage
) {
}
