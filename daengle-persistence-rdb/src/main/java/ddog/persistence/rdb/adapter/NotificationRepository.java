package ddog.persistence.rdb.adapter;

import ddog.domain.notification.Notification;
import ddog.persistence.rdb.jpa.entity.NotificationJpaEntity;
import ddog.persistence.rdb.jpa.repository.NotificationJpaRepository;
import ddog.domain.notification.port.NotificationPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NotificationRepository implements NotificationPersist {

    private final NotificationJpaRepository notificationJpaRepository;

    @Override
    public List<Notification> findNotificationsByUserId(Long userId) {
        List<NotificationJpaEntity> notifications = notificationJpaRepository.findByUserId(userId);

        return notifications.stream()
                .map(NotificationJpaEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void saveNotificationWithLogoutUser(Notification notification) {
        notificationJpaRepository.save(NotificationJpaEntity.from(notification));
    }

    @Override
    public Optional<Notification> findNotificationById(Long notificationId) {
        return notificationJpaRepository.findById(notificationId)
                .map(NotificationJpaEntity::toModel);
    }

    @Override
    public void deleteNotificationById(Long notificationId) {
        notificationJpaRepository.deleteById(notificationId);
    }
}
