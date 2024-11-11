package com.geolocation.geolocation_api.notification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nouveau type de notification adapté au cahier des charges
    private NotificationStatus status;

    private String message;        // Message associé à la notification
    private String deviceName;     // Nom du véhicule ou du boîtier associé
    private Long deviceId;         // ID du boîtier ou du véhicule
    private boolean isRead = false; // Indicateur si la notification a été lue ou non
    private LocalDateTime timestamp; // Horodatage de la notification

    // Méthode pour générer un message par défaut en fonction du statut
    public void generateDefaultMessage() {
        switch (this.status) {
            case DEVICE_DEACTIVATED:
                this.message = "Le boîtier de suivi a été désactivé.";
                break;
            case SPEED_ALERT:
                this.message = "Alerte : Vitesse excessive détectée.";
                break;
            case LOW_FUEL_ALERT:
                this.message = "Alerte : Niveau de carburant bas.";
                break;
            case VEHICLE_MAINTENANCE_ALERT:
                this.message = "Rappel : Maintenance du véhicule nécessaire.";
                break;
            case PAYMENT_PENDING:
                this.message = "Alerte : Paiement en attente.";
                break;
            case HOOD_OPENED:
                this.message = "Alerte : Capot du véhicule ouvert.";
                break;
            case DEVICE_DISCONNECTED:
                this.message = "Le boîtier de suivi a été déconnecté.";
                break;
            case DEVICE_CONNECTED:
                this.message = "Le boîtier de suivi est connecté.";
                break;
            case DEVICE_TAMPERED:
                this.message = "Alerte : Tentative de manipulation du boîtier.";
                break;
            case GPS_COMMAND_SENT:
                this.message = "Commande envoyée au boîtier GPS.";
                break;
            case VEHICLE_MODIFIED:
                this.message = "Les informations du véhicule ont été modifiées.";
                break;
            case CLIENT_NOTIFICATION:
                this.message = "Notification pour le client.";
                break;
            default:
                this.message = "Nouvelle notification reçue.";
                break;
        }
    }
}
