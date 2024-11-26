package ddog.daengleserver.notify;

import ddog.daengleserver.domain.Notification;
import ddog.daengleserver.infrastructure.NotificationJpaRepository;
import ddog.daengleserver.infrastructure.po.NotificationJpaEntity;
import ddog.daengleserver.presentation.notify.dto.NotificationReq;
import ddog.daengleserver.presentation.notify.enums.NotifyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class NotificationTest {

    @Autowired
    private NotificationJpaRepository notificationRepository;

    @Test
    @DisplayName("알림 메시지를 DB에 저장하여 출력할 수 있다.")
    void testSaveNotification() {
        Long userId = 2L;
        String notificationMessage = "테스트 알림 메시지";
        NotifyType notifyType = NotifyType.CERTIFIED;

        NotificationReq data = new NotificationReq(notifyType, notificationMessage, userId);
        Notification notificationData = Notification.createNotificationWithReq(data);

        notificationRepository.save(NotificationJpaEntity.from(notificationData));
        assertThat(notificationData.getNotifyType()).isEqualTo(notificationRepository.findById(1L).get().getNotifyType());
    }
}

