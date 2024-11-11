package com.geolocation.geolocation_api.services.utils;

import com.geolocation.geolocation_api.entities.GpsDevice;
import com.geolocation.geolocation_api.entities.User;
import com.geolocation.geolocation_api.repository.UserRepository;
import com.geolocation.geolocation_api.requests.gpsDevice.GpsDeviceRequest;
import com.geolocation.geolocation_api.responses.gpsDevice.GpsDeviceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GpsDeviceMapper {
    private final UserRepository userRepository ;
    public GpsDeviceResponse toGpsDeviceResponse(GpsDevice gpsDevice){
        return GpsDeviceResponse.builder()
                .id(gpsDevice.getId())
                .imei(gpsDevice.getImei())
                .name(gpsDevice.getNom())
                .serverType(gpsDevice.getServerType())
                .description(gpsDevice.getDescription())
                .simNumber1(gpsDevice.getSimNumber1())
                .simNumber2(gpsDevice.getSimNumber2())
                .smsEmail(gpsDevice.getSmsEmail())
                .vitesseMax(gpsDevice.getVitesseMax())

                // Informations sur la voiture (directement dans GpsDevice)
                .VoitureNom(gpsDevice.getNom())
                .Voituremarque(gpsDevice.getMarque())
                .Voitureimmatricule(gpsDevice.getImmatricule())
                .Voituremodele(gpsDevice.getModele())

                // Informations sur l'utilisateur (le conducteur)
                .idConducteur(gpsDevice.getUser() != null ? gpsDevice.getUser().getId() : null)
                .nomConducteur(gpsDevice.getUser() != null ? gpsDevice.getUser().getFirstName() : null)
                .prenomConducteur(gpsDevice.getUser() != null ? gpsDevice.getUser().getLastName() : null)
                .email_Conducteur(gpsDevice.getUser() != null ? gpsDevice.getUser().getEmail() : null)

                // Statut de l'appareil
                .actif(gpsDevice.isActif())

                // Dates de maintenance
                .insuranceExpiryDate(gpsDevice.getInsuranceExpiryDate())
                .oilChangeDate(gpsDevice.getOilChangeDate())
                .vignetteDeadline(gpsDevice.getVignetteDeadline())

                .fuelAlertEnabled(gpsDevice.isFuelAlertEnabled())
                .hoodOpenAlertEnabled(gpsDevice.isHoodOpenAlertEnabled())
                .speedAlertEnabled(gpsDevice.isSpeedAlertEnabled())
                .deviceConnected(gpsDevice.isDeviceConnected())

                .build();
    }

    public GpsDevice toGPSDevice(GpsDeviceRequest request) {
        User user = null;
        if(request.userId() != null){
          user =  userRepository.findById(request.userId()).orElseThrow(() -> new RuntimeException("User not found"));
        }




        return GpsDevice.builder()
                .imei(request.imei())
                .nom(request.name())
                .actif(request.actif())
                .serverType(request.serverType())
                .description(request.description())
                .simNumber1(request.simNumber1())
                .simNumber2(request.simNumber2())
                .smsEmail(request.smsEmail())
                .vitesseMax(request.vitesseMax())

                // Informations sur la voiture (directement dans GpsDevice)
                .marque(request.voitureMarque())
                .modele(request.voitureModele())
                .immatricule(request.voitureImmatricule())

                // Liaison avec l'utilisateur
                .user(user)

                // Dates de maintenance
                .insuranceExpiryDate(request.insuranceExpiryDate())
                .oilChangeDate(request.oilChangeDate())
                .vignetteDeadline(request.vignetteDeadline())

                // Alertes
                .speedAlertEnabled(request.speedAlertEnabled())
                .fuelAlertEnabled(request.fuelAlertEnabled())
                .hoodOpenAlertEnabled(request.hoodOpenAlertEnabled())
                .deviceConnected(request.deviceConnected())

                .build();
    }
}
