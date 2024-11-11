package com.geolocation.geolocation_api.responses.gpsDevice;

import com.geolocation.geolocation_api.entities.ServerType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder

public record GpsDeviceResponse(
        Long id,
        String imei,
        String name,
        ServerType serverType,
        String description,
        String simNumber1,
        String simNumber2,
        String VoitureNom,
        String Voitureimmatricule,
        String Voituremarque,
        String Voituremodele,
        String smsEmail,
        double vitesseMax,
        boolean actif,
        Long idConducteur,
        String nomConducteur,
        String prenomConducteur,
        //String Nom_Prenom_Conducteur,
        String email_Conducteur,
        LocalDate insuranceExpiryDate,
        LocalDate oilChangeDate,
        LocalDate vignetteDeadline,
        boolean speedAlertEnabled,
        boolean fuelAlertEnabled,
        boolean hoodOpenAlertEnabled,
        boolean deviceConnected

) {
}
