package ddog.chat.application;

import ddog.chat.presentation.dto.ChatMessageReq;
import ddog.chat.presentation.dto.ChatMessagesListResp;
import ddog.chat.presentation.dto.PartnerChatRoomListResp;
import ddog.chat.presentation.dto.UserChatRoomListResp;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.account.port.AccountPersist;
import ddog.domain.chat.ChatMessage;
import ddog.domain.chat.ChatRoom;
import ddog.domain.chat.enums.PartnerType;
import ddog.domain.chat.port.ChatMessagePersist;
import ddog.domain.chat.port.ChatRoomPersist;
import ddog.domain.groomer.Groomer;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.domain.vet.Vet;
import ddog.domain.vet.port.VetPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessagePersist chatMessagePersist;
    private final ChatRoomPersist chatRoomPersist;
    private final UserPersist userPersist;
    private final GroomerPersist groomerPersist;
    private final VetPersist vetPersist;
    private final AccountPersist accountPersist;

    private ChatRoom startChat(Role role, Long accountId, Long otherUserId) {
        return findOrSaveChatRoom(role, accountId, otherUserId);
    }

    public ChatMessagesListResp getAllMessagesByRoomId(Role role, Long userAccountId, Long otherUserId) {
        ChatRoom savedChatRoom = startChat(role, userAccountId, otherUserId);
        
        String otherUserProfile = null;
        if (role.equals(Role.DAENGLE)) {
            Account savedOtherUser = accountPersist.findById(otherUserId);
            if (savedOtherUser.getRole().equals(Role.GROOMER)) {
                Groomer savedGroomer = groomerPersist.findByAccountId(otherUserId).get();
                otherUserProfile = savedGroomer.getImageUrl();
            }
            else if (savedOtherUser.getRole().equals(Role.VET)) {
                Vet savedVet = vetPersist.findByAccountId(otherUserId).get();
                otherUserProfile = savedVet.getImageUrl();
            }
        } else {
            User savedUser = userPersist.findByAccountId(otherUserId).get();
            otherUserProfile = savedUser.getImageUrl();
        }
        
        List<ChatMessage> savedMessages = chatMessagePersist.findByChatRoomId(savedChatRoom.getChatRoomId());

        if (savedMessages.isEmpty()) {
            return ChatMessagesListResp.builder()
                    .roomId(savedChatRoom.getChatRoomId())
                    .userId(savedChatRoom.getUserId())
                    .partnerId(savedChatRoom.getPartnerId())
                    .partnerProfile(otherUserProfile)
                    .messagesGroupedByDate(Collections.emptyMap())
                    .build();
        }

        Map<LocalDate, List<ChatMessagesListResp.ChatMessageSummary>> groupedMessages =
                savedMessages.stream()
                        .collect(Collectors.groupingBy(
                                message -> message.getTimestamp().toLocalDate(),
                                Collectors.mapping(
                                        message -> ChatMessagesListResp.ChatMessageSummary.builder()
                                                .messageId(message.getMessageId())
                                                .messageSenderId(message.getSenderId())
                                                .messageContent(message.getContent())
                                                .messageTime(message.getTimestamp().toLocalTime())
                                                .messageType(message.getMessageType())
                                                .build(),
                                        Collectors.toList()
                                )));

        return ChatMessagesListResp.builder()
                .roomId(savedChatRoom.getChatRoomId())
                .userId(savedChatRoom.getUserId())
                .partnerId(savedChatRoom.getPartnerId())
                .partnerProfile(otherUserProfile)
                .messagesGroupedByDate(groupedMessages)
                .build();
    }

    public UserChatRoomListResp findUserChatRoomList(Long userId, PartnerType partnerType) {
        List<ChatRoom> savedChatRooms = chatRoomPersist.findByUserIdAndPartnerType(userId, partnerType);

        List<UserChatRoomListResp.RoomList> userChatRoomListResps = new ArrayList<>();
        for (ChatRoom savedChatRoom : savedChatRooms) {

            String partnerName = null;
            String partnerProfile = null;

            Account partnerAccount = accountPersist.findById(savedChatRoom.getPartnerId());
            if (partnerAccount.getRole().equals(Role.GROOMER)) {
                Groomer savedGroomer = groomerPersist.findByAccountId(partnerAccount.getAccountId()).get();
                partnerName = savedGroomer.getName();
                partnerProfile = savedGroomer.getImageUrl();
            } else if (partnerAccount.getRole().equals(Role.VET)) {
                Vet savedVet = vetPersist.findByAccountId(partnerAccount.getAccountId()).get();
                partnerName = savedVet.getName();
                partnerProfile = savedVet.getImageUrl();
            }
            ChatMessage savedLastMessages = chatMessagePersist.findLatestMessageByRoomId(savedChatRoom.getChatRoomId());
            String lastMessage = (savedLastMessages != null) ? savedLastMessages.getContent() : "";
            String messageTime = (savedLastMessages != null)
                    ? savedLastMessages.getTimestamp().toLocalTime().toString()
                    : "";

            userChatRoomListResps.add(UserChatRoomListResp.RoomList.builder()
                    .roomId(savedChatRoom.getChatRoomId())
                    .partnerId(savedChatRoom.getPartnerId())
                    .partnerName(partnerName)
                    .partnerProfile(partnerProfile)
                    .messageTime(messageTime)
                    .lastMessage(lastMessage)
                    .partnerType(savedChatRoom.getPartnerType())
                    .build());
        }

        return UserChatRoomListResp.builder()
                .roomList(userChatRoomListResps)
                .build();
    }

    public boolean deleteChatRoom(Long roomId) {
        ChatRoom savedChatRoom = chatRoomPersist.findByRoomId(roomId);
        chatRoomPersist.exitChatRoom(savedChatRoom.getUserId(), savedChatRoom.getPartnerId());

        return true;
    }

    public ChatMessage sendAndSaveMessage(ChatMessageReq chatMessageReq, Long roomId, Long accountId){
        Long recipientId = findMessageRecipientByRoomId(roomId, chatMessageReq.getSenderId());
        Long messageId = System.currentTimeMillis();

        ChatMessage chatMessage = ChatMessage.builder()
                .messageId(messageId)
                .chatRoomId(roomId)
                .messageType(chatMessageReq.getMessageType())
                .senderId(accountId)
                .content(chatMessageReq.getMessageContent())
                .recipientId(recipientId)
                .timestamp(LocalDateTime.now())
                .build();

        return chatMessagePersist.save(chatMessage);
    }

    public PartnerChatRoomListResp findPartnerChatRoomList(Long userId) {
        List<ChatRoom> savedChatRooms = chatRoomPersist.findByPartnerId(userId);

        List<PartnerChatRoomListResp.RoomList> partnerChatRoomListResps = new ArrayList<>();
        for (ChatRoom savedChatRoom : savedChatRooms) {

            User savedUser = userPersist.findByAccountId(savedChatRoom.getUserId()).get();

            ChatMessage savedLastMessages = chatMessagePersist.findLatestMessageByRoomId(savedChatRoom.getChatRoomId());

            partnerChatRoomListResps.add(PartnerChatRoomListResp.RoomList.builder()
                    .roomId(savedChatRoom.getChatRoomId())
                    .partnerId(savedChatRoom.getUserId())
                    .partnerName(savedUser.getNickname())
                    .partnerProfile(savedUser.getImageUrl())
                    .messageTime(savedLastMessages.getTimestamp().toLocalTime().toString())
                    .lastMessage(savedLastMessages.getContent())
                    .build());

        }
        return PartnerChatRoomListResp.builder()
                .roomList(partnerChatRoomListResps)
                .build();
    }

    private ChatRoom findOrSaveChatRoom(Role role, Long accountId, Long otherUserId) {
        ChatRoom toSaveChat = null;
        if (role.equals(Role.DAENGLE)) {
            if (accountPersist.findById(otherUserId).getRole().equals(Role.VET)){
                toSaveChat = chatRoomPersist.enterChatRoom(accountId, otherUserId, PartnerType.VET_PARTNER);
            } else if (accountPersist.findById(otherUserId).getRole().equals(Role.GROOMER)) {
                toSaveChat = chatRoomPersist.enterChatRoom(accountId, otherUserId, PartnerType.GROOMER_PARTNER);
            }
        } else {
            if (role.equals(Role.GROOMER)) {
                toSaveChat = chatRoomPersist.enterChatRoom(otherUserId, accountId, PartnerType.GROOMER_PARTNER);
            } else if (role.equals(Role.VET)) {
                toSaveChat = chatRoomPersist.enterChatRoom(otherUserId, accountId, PartnerType.VET_PARTNER);
            }
        }
        return toSaveChat;
    }

    private Long findMessageRecipientByRoomId(Long roomId, Long senderId) {
        ChatRoom savedChatRoom = chatRoomPersist.findByRoomId(roomId);
        if (savedChatRoom.getUserId().equals(senderId)) return savedChatRoom.getPartnerId();
        else return savedChatRoom.getUserId();
    }
}
