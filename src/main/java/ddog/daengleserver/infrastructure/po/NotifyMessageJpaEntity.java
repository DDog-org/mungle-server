package ddog.daengleserver.infrastructure.po;

import ddog.daengleserver.domain.NotifyMessage;
import ddog.daengleserver.global.notify.enums.NotifyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "NotifyMessage")
public class NotifyMessageJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private NotifyType notifyType;

    private String message;

    private Long userId;


    public static NotifyMessageJpaEntity from(NotifyMessage notifyMessage) {
        return NotifyMessageJpaEntity.builder()
                .id(notifyMessage.getId())
                .notifyType(notifyMessage.getNotifyType())
                .message(notifyMessage.getMessage())
                .userId(notifyMessage.getUserId())
                .build();
    }

    public NotifyMessage toModel() {
        return NotifyMessage.builder()
                .id(this.id)
                .notifyType(this.notifyType)
                .message(this.message)
                .userId(this.userId)
                .build();
    }
}
