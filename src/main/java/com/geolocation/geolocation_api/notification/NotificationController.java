package com.geolocation.geolocation_api.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    /**
     * Retrieves unread notifications ordered by device ID.
     *
     * @return a ResponseEntity containing a list of unread notifications ordered by device ID
     */
    @GetMapping("unreadNotificationsOrderByDeviceId")
    public ResponseEntity<List<Notification>> getUnreadNotificationsOrderByDeviceId(){
        return ResponseEntity.ok(notificationService.getUnreadNotificationsOrderByDeviceId());
    }

    /**
     * Retrieves unread notifications ordered by device ID and timestamp.
     *
     * @return a ResponseEntity containing a list of unread notifications ordered by device ID and timestamp
     */
    @GetMapping("unreadNotificationsOrderByDeviceIdAndTimestamp")
    public ResponseEntity<List<Notification>> getUnreadNotificationsOrderByDeviceIdAndTimestamp(){
        return ResponseEntity.ok(notificationService.getUnreadNotificationsOrderByDeviceIdAndTimestamp());
    }

    @GetMapping ("AllNotificationsOrderByDeviceIdAndTimestamp")
    public ResponseEntity<List<Notification>>getAllNotificationsOrderByDeviceIdAndTimestamp(){
        return ResponseEntity.ok(notificationService.getAllNotificationsOrderByDeviceIdAndTimestamp());
    }


    /**
     * Retrieves notifications for a specific device.
     *
     * @param deviceId the ID of the device
     * @return a ResponseEntity containing a list of notifications for the specified device
     */
//    @GetMapping("notificationsOfDevice/{deviceId}")
//    public ResponseEntity<List<Notification>> getNotificationsOfDevice(@PathVariable Long deviceId){
//        return ResponseEntity.ok(notificationService.getNotificationsOfDevice(deviceId));
//    }

    @GetMapping("notificationsOfDeviceOrderByTimestamp/{deviceId}")
    public ResponseEntity<List<Notification>> getNotificationsOfDeviceOrderByTimestamp(@PathVariable Long deviceId){
        return ResponseEntity.ok(notificationService.getNotificationsOfDeviceOrderByTimestamp(deviceId));
    }

    /**
     * Retrieves notifications by their status.
     *
     * @param status the status of the notifications
     * @return a ResponseEntity containing a list of notifications with the specified status
     */
    @GetMapping("notificationsByStatus/{status}")
    public ResponseEntity<List<Notification>> getNotificationsByStatus(@PathVariable NotificationStatus status){
        return ResponseEntity.ok(notificationService.getNotificationsByStatus(status));
    }

    /**
     * Retrieves all unread notifications.
     *
     * @return a ResponseEntity containing a list of all unread notifications
     */
    @GetMapping("unreadNotifications")
    public ResponseEntity<List<Notification>> getAllUnreadNotifications(){
        return ResponseEntity.ok(notificationService.getAllUnreadNotifications());
    }

    /**
     * Retrieves all notifications.
     *
     * @return a ResponseEntity containing a list of all notifications
     */
    @GetMapping("allNotifications")
    public ResponseEntity<List<Notification>> getAllNotifications(){
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    /**
     * Marks a notification as read.
     *
     * @param notificationId the ID of the notification to be marked as read
     * @return a ResponseEntity containing the updated notification
     */
    @PutMapping("markNotificationAsRead/{notificationId}")
    public ResponseEntity<Notification> markNotificationAsRead(@PathVariable Long notificationId){
        return ResponseEntity.ok(notificationService.markNotificationAsRead(notificationId));
    }

    @PutMapping("markAllNotificationAsReadOfDevice/{deviceId}")
    public ResponseEntity<Void> markAllNotificationAsReadByDeviceID(@PathVariable Long deviceId){
        notificationService.markAllNotificationAsReadByDeviceID(deviceId);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes notifications for a specific device.
     *
     * @param deviceId the ID of the device
     * @return a ResponseEntity indicating the result of the delete operation
     */
    @DeleteMapping("deleteNotificationsOfDevice/{deviceId}")
    public ResponseEntity<Void> deleteNotificationsOfDevice(@PathVariable Long deviceId){
        notificationService.deleteNotificationsOfDevice(deviceId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("deleteAllNotifications")
    public ResponseEntity<Void> deleteAllNotifications(){
        notificationService.deleteAllNotifications();
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("deleteNotification/{notificationId}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long notificationId){
        notificationService.deleteNotification(notificationId);
        return ResponseEntity.ok().build();
    }
}
