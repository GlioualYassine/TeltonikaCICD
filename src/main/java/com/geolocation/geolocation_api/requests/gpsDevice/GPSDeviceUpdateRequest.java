package com.geolocation.geolocation_api.requests.gpsDevice;

import com.geolocation.geolocation_api.entities.ServerType;

import java.time.LocalDate;

public record GPSDeviceUpdateRequest(
        Long id,
        String imei,
        String name,
        ServerType serverType,
        String description,
        String simNumber1,
        String simNumber2,
        String smsEmail,
        double vitesseMax,
        boolean actif,
        LocalDate insuranceExpiryDate,   // Date d'expiration de l'assurance
        LocalDate oilChangeDate,         // Date de la prochaine vidange
        LocalDate vignetteDeadline,      // Date limite pour la vignette

        // Informations de la voiture
        String voitureNom,               // Nom de la voiture
        String voitureMarque,            // Marque de la voiture
        String voitureModele,            // Modèle de la voiture
        String voitureImmatricule        // Immatriculation de la voiture
) {
}
