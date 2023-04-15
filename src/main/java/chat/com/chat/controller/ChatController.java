package chat.com.chat.controller;

import chat.com.chat.dto.AddingMessageDto;
import chat.com.chat.dto.ChatMessageResponseDto;
import chat.com.chat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatMessageResponseDto>> getChatHistory(@RequestAttribute UUID userId,
                                                                       @RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.ok(chatService.findChatHistory(userId, page, size));
    }

    @PostMapping
    public ResponseEntity<Boolean> sendMessage(@RequestAttribute UUID userId,
                                                                    @RequestBody AddingMessageDto message) {
        return ResponseEntity.ok(chatService.sendMessage(userId, message));
    }

    @PutMapping("/{messageId}")
    public ResponseEntity<Boolean> updateMessage(@RequestAttribute UUID userId,
                                                                      @PathVariable UUID messageId,
                                                                      @RequestBody AddingMessageDto message) {
        return ResponseEntity.ok(chatService.updateMessage(userId, messageId, message));
    }

    @DeleteMapping("/{messageId}")
    public ResponseEntity<Boolean> deleteMessage(@RequestAttribute UUID userId, @PathVariable UUID messageId) {
        return ResponseEntity.ok(chatService.deleteMessage(userId, messageId));
    }
}
