package com.geolocation.geolocation_api.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Récupérer toutes les notifications non lues pour un utilisateur spécifique
    List<Notification> findByIsReadFalseAndDeviceId(Long deviceId);

    // Récupérer toutes les notifications pour un utilisateur spécifique
    List<Notification> findByDeviceId(Long deviceId);

    // Récupérer toutes les notifications avec un certain statut
    List<Notification> findByStatus(NotificationStatus status);

    // Récupérer toutes les notifications non lues
    List<Notification> findByIsReadFalse();

    // Récupérer toutes les notifications non lues, triées par DeviceId
    List<Notification> findByIsReadFalseOrderByDeviceIdAsc();

    // Récupérer toutes les notifications non lues, triées par DeviceId et timestamp
    List<Notification> findByIsReadFalseOrderByDeviceIdAscTimestampDesc();

    // Récupérer toutes les notifications non lues, triées par DeviceId et timestamp
    List<Notification> findAllByOrderByDeviceIdAscTimestampDesc();

    // Supprimer toutes les notifications d'un appareil spécifique
    void deleteByDeviceId(Long deviceId);

    List<Notification> findByDeviceIdOrderByTimestampDesc(Long deviceId);
}
