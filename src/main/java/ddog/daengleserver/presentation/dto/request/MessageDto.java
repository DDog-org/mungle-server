package ddog.daengleserver.presentation.dto.request;

import lombok.Data;

@Data
public class MessageDto {
    private String targetUserId;
    private String message;
}
