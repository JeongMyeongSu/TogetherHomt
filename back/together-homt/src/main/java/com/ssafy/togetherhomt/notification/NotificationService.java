package com.ssafy.togetherhomt.notification;

import com.ssafy.togetherhomt.common.CommonService;
import com.ssafy.togetherhomt.user.User;
import com.ssafy.togetherhomt.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class NotificationService {

    /*** Service ***/
    private CommonService commonService;
    /*** Repository ***/
    private NotificationRepository notificationRepository;
    private UserRepository userRepository;


    /*** 알림 조회 ***/
    public List<NotificationDto> getNotification() {
        List<NotificationDto> notificationList = new ArrayList<>();
        for (Notification notification : notificationRepository.findByReceiver(commonService.getLoginUser()))
            notificationList.add(commonService.builder(notification));
        return notificationList;
    }

    /*** 알림 전송 ***/
    public Notification sendNotification(NotificationDto notificationDto) {
        User receiver = userRepository.findByEmail(notificationDto.getReceiver().getEmail());
        if (receiver == null)
            return null;

        User sender = userRepository.findByEmail(commonService.getLoginUser().getEmail());

        Notification notification = Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .notificationType(notificationDto.getNotificationType())
                .build();
        return notificationRepository.save(notification);
    }

    /*** 알림 확인(삭제) ***/
    public Notification readNotification(Long notificationId) {
        Optional<Notification> optNotification = notificationRepository.findById(notificationId);
        if (!optNotification.isPresent())
            return null;

        Notification notification = optNotification.get();

        User me = commonService.getLoginUser();
        if (!me.getEmail().equals(notification.getReceiver().getEmail()))
            return null;

        notificationRepository.delete(notification);
        return notification;
    }

    /*** 재촉 알림 초기화 ***/
    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")    // 0초 0분 0시 매일 매월 아무 요일
    public void clearPushNotification() {
        List<Notification> pushList = notificationRepository.findByNotificationTypeLike(NotificationType.PUSH);
        log.info("--------------- Clear [PUSH] Notifications ---------------");
//        for (Notification push : pushList)
//            System.out.println(push.getSender().getEmail() + " -----> " + push.getReceiver().getEmail());
        notificationRepository.deleteAll(pushList);
    }

}
