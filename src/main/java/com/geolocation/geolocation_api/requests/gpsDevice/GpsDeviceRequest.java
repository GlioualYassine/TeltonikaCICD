package com.geolocation.geolocation_api.requests.gpsDevice;

import com.geolocation.geolocation_api.entities.ServerType;

import java.time.LocalDate;

public record GpsDeviceRequest(
        //Long id,
        String imei,
        String name,
        boolean actif,
        ServerType serverType,
        String description,
        String simNumber1,
        String simNumber2,
        String smsEmail,
        double vitesseMax,
        Long userId,                     // Référence à l'utilisateur qui possède cet appareil

        // Informations de la voiture
        String voitureNom,               // Nom de la voiture
        String voitureMarque,            // Marque de la voiture
        String voitureModele,            // Modèle de la voiture
        String voitureImmatricule,       // Immatriculation de la voiture

        // Dates de maintenance
        LocalDate insuranceExpiryDate,   // Date d'expiration de l'assurance
        LocalDate oilChangeDate,         // Date de la prochaine vidange
        LocalDate vignetteDeadline ,      // Date limite pour la vignette

        //alertes
        boolean speedAlertEnabled,      // Alerte pour vitesse excessive activée
        boolean fuelAlertEnabled,        // Alerte pour niveau de carburant bas activée
        boolean hoodOpenAlertEnabled,     // Alerte pour ouverture du capot activée
        boolean deviceConnected
) {
}
