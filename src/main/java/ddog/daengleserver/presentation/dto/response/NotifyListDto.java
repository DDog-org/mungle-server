package ddog.daengleserver.presentation.dto.response;

import ddog.daengleserver.global.notify.enums.NotifyType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotifyListDto {
    private NotifyType notifyType;
    private String message;
    private Long userId;
}
