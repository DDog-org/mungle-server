package ddog.daengleserver.domain;
import ddog.daengleserver.global.notify.enums.NotifyType;
import ddog.daengleserver.presentation.dto.response.NotifyListDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class NotifyMessage {

    private final Long id;
    private final NotifyType notifyType;
    private final String message;
    private final Long userId; // 알림대상


    public NotifyMessage withUserId(Long userId) {
        return NotifyMessage.builder()
                .id(this.id)
                .notifyType(this.notifyType)
                .message(this.message)
                .userId(userId)
                .build();
    }

    public NotifyListDto getNotification() {
        return NotifyListDto.builder()
                .notifyType(this.notifyType)
                .message(this.message)
                .userId(this.userId)
                .build();
    }
}
