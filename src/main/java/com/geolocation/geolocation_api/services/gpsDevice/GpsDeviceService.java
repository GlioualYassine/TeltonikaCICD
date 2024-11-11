package com.geolocation.geolocation_api.services.gpsDevice;

import com.geolocation.geolocation_api.entities.GpsDevice;
import com.geolocation.geolocation_api.entities.User;
import com.geolocation.geolocation_api.notification.Notification;
import com.geolocation.geolocation_api.notification.NotificationService;
import com.geolocation.geolocation_api.notification.NotificationStatus;
import com.geolocation.geolocation_api.repository.GpsDeviceRepository;
import com.geolocation.geolocation_api.requests.gpsDevice.GPSDeviceUpdateRequest;
import com.geolocation.geolocation_api.requests.gpsDevice.GpsDeviceRequest;
import com.geolocation.geolocation_api.responses.gpsDevice.GpsDeviceResponse;
import com.geolocation.geolocation_api.services.auth.AuthenticationService;
import com.geolocation.geolocation_api.services.utils.GpsDeviceMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GpsDeviceService {
    private static final Logger log = LoggerFactory.getLogger(GpsDeviceService.class);
    private final GpsDeviceRepository gpsDeviceRepository;
    private final GpsDeviceMapper mapper;
    private final NotificationService notificationService;
    private final AuthenticationService authenticationService;

    public List<GpsDeviceResponse> findAll(){
        return gpsDeviceRepository.findAll().stream().map(mapper::toGpsDeviceResponse).collect(Collectors.toList());
    }

    public GpsDevice getById(Long id){
        return gpsDeviceRepository.findById(id).orElse(null);
    }

    public GpsDeviceResponse findById(Long id){
        return gpsDeviceRepository.findById(id)
                .map(mapper::toGpsDeviceResponse)
                .orElseThrow(() -> new RuntimeException("GpsDevice not found"));
    }

    public GpsDeviceResponse createGpsDevice(GpsDeviceRequest request){
        GpsDevice gpsDevice = mapper.toGPSDevice(request);

        // Sauvegarder l'appareil GPS
        GpsDevice savedGpsDevice = gpsDeviceRepository.save(gpsDevice);

        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            User user = authenticationService.getAuthenticatedUser();
            log.warn("User {}", user);
            notificationService.sendNotification(
                    user.getId(),
                    Notification.builder()
                            .status(NotificationStatus.DEVICE_CONNECTED)
                            .deviceId(savedGpsDevice.getId())
                            .deviceName(savedGpsDevice.getNom())
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }

        // Vérifier si une alerte de maintenance doit être envoyée
        checkMaintenanceAlerts(savedGpsDevice);

        return mapper.toGpsDeviceResponse(savedGpsDevice);
    }

    public GpsDevice deleteGpsDevice(Long id){
        GpsDevice gpsDevice = gpsDeviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GpsDevice not found"));
        gpsDeviceRepository.deleteById(id);

        if(SecurityContextHolder.getContext().getAuthentication() != null) {
            User user = authenticationService.getAuthenticatedUser();
            log.warn("User {}", user);
            notificationService.sendNotification(
                    user.getId(),
                    Notification.builder()
                            .status(NotificationStatus.DEVICE_DISCONNECTED)
                            .deviceId(gpsDevice.getId())
                            .deviceName(gpsDevice.getNom())
                            .message("Device Deleted By " + user.getFirstName() + " " + user.getLastName())
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }
        return gpsDevice;
    }

    public GpsDeviceResponse updateGpsDevice(GPSDeviceUpdateRequest gpsDeviceRequest, Long id) {
        GpsDevice gpsDeviceToUpdate = gpsDeviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GpsDevice not found"));

        // Mettre à jour les champs de l'appareil
        gpsDeviceToUpdate.setImei(gpsDeviceRequest.imei());
        gpsDeviceToUpdate.setNom(gpsDeviceRequest.name());
        gpsDeviceToUpdate.setActif(gpsDeviceRequest.actif());
        gpsDeviceToUpdate.setServerType(gpsDeviceRequest.serverType());
        gpsDeviceToUpdate.setDescription(gpsDeviceRequest.description());
        gpsDeviceToUpdate.setSimNumber1(gpsDeviceRequest.simNumber1());
        gpsDeviceToUpdate.setSimNumber2(gpsDeviceRequest.simNumber2());
        gpsDeviceToUpdate.setSmsEmail(gpsDeviceRequest.smsEmail());
        gpsDeviceToUpdate.setVitesseMax(gpsDeviceRequest.vitesseMax());

        // Mettre à jour les dates de maintenance
        gpsDeviceToUpdate.setInsuranceExpiryDate(gpsDeviceRequest.insuranceExpiryDate());
        gpsDeviceToUpdate.setOilChangeDate(gpsDeviceRequest.oilChangeDate());
        gpsDeviceToUpdate.setVignetteDeadline(gpsDeviceRequest.vignetteDeadline());

        GpsDevice updatedGpsDevice = gpsDeviceRepository.save(gpsDeviceToUpdate);

        // Vérifier si une alerte de maintenance doit être envoyée
        checkMaintenanceAlerts(updatedGpsDevice);

        return mapper.toGpsDeviceResponse(updatedGpsDevice);
    }

    /**
     * Vérifie si des alertes automatiques doivent être envoyées pour les dates de maintenance
     */
    private void checkMaintenanceAlerts(GpsDevice gpsDevice) {
        LocalDate today = LocalDate.now();

        // Alerte pour expiration de l'assurance
        if (gpsDevice.getInsuranceExpiryDate() != null && gpsDevice.getInsuranceExpiryDate().isBefore(today.plusDays(3))) {
            sendMaintenanceAlert(gpsDevice, "L'assurance de l'appareil expire bientôt.");
        }

        // Alerte pour vidange
        if (gpsDevice.getOilChangeDate() != null && gpsDevice.getOilChangeDate().isBefore(today.plusDays(3))) {
            sendMaintenanceAlert(gpsDevice, "La vidange de l'appareil est due dans 3 jours.");
        }

        // Alerte pour la vignette
        if (gpsDevice.getVignetteDeadline() != null && gpsDevice.getVignetteDeadline().isBefore(today.plusDays(3))) {
            sendMaintenanceAlert(gpsDevice, "La vignette de l'appareil expire dans 3 jours.");
        }
    }

    /**
     * Méthode pour envoyer une alerte de maintenance
     */
    private void sendMaintenanceAlert(GpsDevice gpsDevice, String message) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            User user = authenticationService.getAuthenticatedUser();
            notificationService.sendNotification(
                    user.getId(),
                    Notification.builder()
                            .status(NotificationStatus.VEHICLE_MAINTENANCE_ALERT)
                            .deviceId(gpsDevice.getId())
                            .deviceName(gpsDevice.getNom())
                            .message(message)
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }
    }

    public GpsDeviceResponse findByImei(String imei) {
        return gpsDeviceRepository.findByImei(imei)
                .map(mapper::toGpsDeviceResponse)
                .orElseThrow(() -> new RuntimeException("GpsDevice not found"));
    }

    public List<GpsDeviceResponse> findByUser(Long userId) {
        return gpsDeviceRepository.findByUserId(userId).stream()
                .map(mapper::toGpsDeviceResponse)
                .collect(Collectors.toList());
    }
}
