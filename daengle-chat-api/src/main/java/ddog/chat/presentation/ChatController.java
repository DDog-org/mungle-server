package ddog.chat.presentation;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.chat.application.ChatService;
import ddog.chat.presentation.dto.ChatMessageReq;
import ddog.chat.presentation.dto.ChatMessagesListResp;
import ddog.chat.presentation.dto.PartnerChatRoomListResp;
import ddog.chat.presentation.dto.UserChatRoomListResp;
import ddog.domain.chat.ChatMessage;
import ddog.domain.chat.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public CommonResponseEntity<ChatMessagesListResp> startChatMessage(PayloadDto payloadDto, @RequestParam Long otherUserId) {
        return success(chatService.getAllMessagesByRoomId(payloadDto.getRole(), payloadDto.getAccountId(), otherUserId));
    }

    @DeleteMapping("/delete/{roomId}")
    public CommonResponseEntity<Boolean> deleteChatRoom(@PathVariable Long roomId) {
        return success(chatService.deleteChatRoom(roomId));
    }

    // 메시지 전송 post ("/api/chat/messages/{roomId}")
//    @PostMapping("/messages/{roomId}")
    @PostMapping("/messages/{roomId}")
    public CommonResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessageReq messageReq, @PathVariable Long roomId) {
        ChatMessage savedMessage = chatService.sendAndSaveMessage(messageReq, roomId);
        messagingTemplate.convertAndSend("/sub/" + roomId, savedMessage);
        return success(savedMessage);
    }

    // 사용자 채팅방 목록 Get ("/api/chat/user/accoutId")
    @GetMapping("/user/list")
    public CommonResponseEntity<UserChatRoomListResp> findAllUserChatRoomList(PayloadDto payloadDto){
        return success(chatService.findUserChatRoomList(payloadDto.getAccountId()));
    }

    // 파트너 채팅방 목록 GET ("/api/chat/groomer|vet/{accountId}")
    @GetMapping("/groomer/list")
    public CommonResponseEntity<PartnerChatRoomListResp> findGroomerChatRoomList(PayloadDto payloadDto){
        return success(chatService.findPartnerChatRoomList(payloadDto.getAccountId()));
    }

    @GetMapping("/vet/list")
    public CommonResponseEntity<PartnerChatRoomListResp> findVetChatRoomList(PayloadDto payloadDto){
        return success(chatService.findPartnerChatRoomList(payloadDto.getAccountId()));
    }

}
