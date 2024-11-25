package ddog.daengleserver.application.repository;

import ddog.daengleserver.domain.Notification;
import java.util.List;

public interface NotificationRepository {

    List<Notification> findByUserId(Long userId);

    void save(Notification notification);

    Notification findById(Long notificationId);

    void delete(Long notificationId);

}
