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
        private Long otherId;
        private String otherName;
        private String otherProfile;
        private String messageTime;
        private String lastMessage;
    }

}
