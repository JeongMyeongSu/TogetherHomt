package com.ssafy.togetherhomt.notification;

import com.ssafy.togetherhomt.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByReceiver(User me);
    List<Notification> findByNotificationTypeLike(NotificationType type);

}
