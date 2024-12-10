package ddog.domain.notification.port;


import ddog.domain.notification.Notification;

import java.util.List;

public interface NotificationPersist {

    List<Notification> findByUserId(Long userId);

    void save(Notification notification);

    Notification findById(Long notificationId);

    void delete(Long notificationId);

}
