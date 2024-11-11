package com.geolocation.geolocation_api.notification;

public enum NotificationStatus {
    DEVICE_DEACTIVATED,            // Boîtier de suivi désactivé
    SPEED_ALERT,                   // Vitesse excessive
    LOW_FUEL_ALERT,                // Niveau de carburant bas
    VEHICLE_MAINTENANCE_ALERT,     // Notification de maintenance (vignettes, vidanges, etc.)
    PAYMENT_PENDING,               // Paiement en attente
    HOOD_OPENED,                   // Capot du véhicule ouvert
    DEVICE_DISCONNECTED,           // Boîtier de suivi déconnecté
    DEVICE_CONNECTED,              // Boîtier de suivi connecté
    DEVICE_TAMPERED,               // Tentative de désactivation ou de manipulation du boîtier
    GPS_COMMAND_SENT,              // Commande envoyée au boîtier GPS (par exemple via Teltonika)
    VEHICLE_MODIFIED,              // Informations sur le véhicule modifiées
    CLIENT_NOTIFICATION,           // Notification générique pour le client
}
