package ddog.persistence.po;

import ddog.daengleserver.domain.Notification;
import ddog.daengleserver.presentation.notify.enums.NotifyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Notification")
public class NotificationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private NotifyType notifyType;

    private String message;

    private Long userId;


    public static NotificationJpaEntity from(Notification notification) {
        return NotificationJpaEntity.builder()
                .id(notification.getId())
                .notifyType(notification.getNotifyType())
                .message(notification.getMessage())
                .userId(notification.getUserId())
                .build();
    }

    public Notification toModel() {
        return Notification.builder()
                .id(this.id)
                .notifyType(this.notifyType)
                .message(this.message)
                .userId(this.userId)
                .build();
    }
}
