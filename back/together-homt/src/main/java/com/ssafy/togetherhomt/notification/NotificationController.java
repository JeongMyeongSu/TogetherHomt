package com.ssafy.togetherhomt.notification;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/notification/notifications")
@AllArgsConstructor
public class NotificationController {

    private NotificationService notificationService;


    @GetMapping
    public ResponseEntity<?> getNotification() {
        return new ResponseEntity<>(notificationService.getNotification(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationDto notificationDto) {
        Notification notification = notificationService.sendNotification(notificationDto);
        if (notification == null)
            return new ResponseEntity<>("Couldn't send notification. Receiver may be incorrect.", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Notification sent.", HttpStatus.OK);
    }

    @DeleteMapping("/{notification-id}")
    public ResponseEntity<?> readNotification(@PathVariable("notification-id") Long notificationId) {
        Notification notification = notificationService.readNotification(notificationId);
        if (notification == null)
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
