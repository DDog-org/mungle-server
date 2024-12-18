package ddog.chat.presentation.dto;

import ddog.domain.chat.enums.PartnerType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UserChatRoomListResp {
    private List<RoomList> roomList;

    @Builder
    @Getter
    public static class RoomList {
        private Long roomId;
        private Long otherId;
        private String otherName;
        private String otherProfile;
        private String messageTime;
        private String lastMessage;
        private PartnerType partnerType;
    }

}
