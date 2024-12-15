package ddog.chat.presentation.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class PartnerChatRoomListResp {
    private List<RoomList> roomList;

    @Builder
    @Getter
    public static class RoomList {
        private Long roomId;
        private Long partnerId;
        private String partnerName;
        private String partnerProfile;
        private String messageTime;
        private String lastMessage;
    }

}
