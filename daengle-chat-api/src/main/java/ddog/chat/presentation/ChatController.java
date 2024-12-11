package ddog.chat.presentation;

import ddog.chat.application.ChatService;
import ddog.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/{roomId}")
    @SendTo("/queue/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable Long roomId, ChatMessage message) {
        chatService.saveMessage(roomId, message);
        return message;
    }

    @PostMapping("/messages")
    public ResponseEntity<ChatMessage> saveMessage(@RequestBody ChatMessage message) {
        ChatMessage savedMessage = chatService.saveMessage(1L, message); // 예시 저장
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("/enter/{roomId}")
    public List<ChatMessage> findMessagesByRoomId(@PathVariable Long roomId) {
        return chatService.findMessagesByRoomId(roomId);
    }
}
