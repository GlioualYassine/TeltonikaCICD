package com.geolocation.geolocation_api.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    // Messaging template for sending notifications
    private final SimpMessagingTemplate messagingTemplate;

    // Repository for accessing notification data
    private final NotificationRepository notificationRepository;

    /**
     * Sends a notification to a specific user.
     *
     * @param userId the ID of the user to send the notification to
     * @param notification the notification to be sent
     */
    public void sendNotification(Long userId, Notification notification) {
        // Generate a default message based on the notification status
        notification.generateDefaultMessage();

        // Persist the notification before sending it
        notificationRepository.save(notification);

        log.info("Sending notification to user: {} , notification: {}", userId, notification);

        // Send the notification using WebSockets
        messagingTemplate.convertAndSendToUser(userId.toString(), "/notifications", notification);
    }

    /**
     * Retrieves all notifications.
     *
     * @return a list of all notifications
     */
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    /**
     * Retrieves notifications of a specific device, ordered by timestamp.
     *
     * @param deviceId the ID of the device
     * @return a list of notifications for the specified device
     */
    public List<Notification> getNotificationsOfDeviceOrderByTimestamp(Long deviceId) {
        return notificationRepository.findByDeviceIdOrderByTimestampDesc(deviceId);
    }

    /**
     * Retrieves all unread notifications.
     *
     * @return a list of all unread notifications
     */
    public List<Notification> getAllUnreadNotifications() {
        return notificationRepository.findByIsReadFalse();
    }

    /**
     * Retrieves unread notifications for a specific device.
     *
     * @param deviceId the ID of the device
     * @return a list of unread notifications for the specified device
     */
    public List<Notification> getUnreadNotifications(Long deviceId) {
        return notificationRepository.findByIsReadFalseAndDeviceId(deviceId);
    }

    /**
     * Retrieves notifications by their status.
     *
     * @param status the status of the notifications
     * @return a list of notifications with the specified status
     */
    public List<Notification> getNotificationsByStatus(NotificationStatus status) {
        return notificationRepository.findByStatus(status);
    }

    /**
     * Retrieves unread notifications ordered by device ID.
     *
     * @return a list of unread notifications ordered by device ID
     */
    public List<Notification> getUnreadNotificationsOrderByDeviceId() {
        return notificationRepository.findByIsReadFalseOrderByDeviceIdAsc();
    }

    /**
     * Retrieves unread notifications ordered by device ID and timestamp.
     *
     * @return a list of unread notifications ordered by device ID and timestamp
     */
    public List<Notification> getUnreadNotificationsOrderByDeviceIdAndTimestamp() {
        return notificationRepository.findByIsReadFalseOrderByDeviceIdAscTimestampDesc();
    }

    /**
     * Deletes notifications for a specific device.
     *
     * @param deviceId the ID of the device
     */
    public void deleteNotificationsOfDevice(Long deviceId) {
        notificationRepository.deleteByDeviceId(deviceId);
        log.info("Deleted notifications for device with ID: {}", deviceId);
    }

    /**
     * Marks a notification as read.
     *
     * @param notificationId the ID of the notification to be marked as read
     * @return the updated notification
     */
    public Notification markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found with ID: " + notificationId));

        notification.setRead(true);
        notificationRepository.save(notification);

        log.info("Marked notification as read: {}", notification);
        return notification;
    }

    /**
     * Deletes all notifications.
     */
    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
        log.info("Deleted all notifications");
    }

    /**
     * Deletes a specific notification by its ID.
     *
     * @param notificationId the ID of the notification
     */
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
        log.info("Deleted notification with ID: {}", notificationId);
    }

    /**
     * Retrieves all notifications ordered by device ID and timestamp.
     *
     * @return a list of notifications ordered by device ID and timestamp
     */
    public List<Notification> getAllNotificationsOrderByDeviceIdAndTimestamp() {
        return notificationRepository.findAllByOrderByDeviceIdAscTimestampDesc();
    }

    /**
     * Marks all notifications as read for a specific device.
     *
     * @param deviceId the ID of the device
     */
    public void markAllNotificationAsReadByDeviceID(Long deviceId) {
        List<Notification> notifications = notificationRepository.findByDeviceId(deviceId);

        notifications.forEach(notification -> {
            notification.setRead(true);
        });

        notificationRepository.saveAll(notifications);

        log.info("Marked all notifications as read for device with ID: {}", deviceId);
    }
}
