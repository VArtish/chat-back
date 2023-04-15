package chat.com.chat.service;

import chat.com.chat.dto.AddingMessageDto;
import chat.com.chat.dto.ChatMessageResponseDto;

import java.util.List;
import java.util.UUID;

public interface ChatService {

    Boolean sendMessage(UUID userId, AddingMessageDto message);

    Boolean updateMessage(UUID userId, UUID messageId, AddingMessageDto message);

    Boolean deleteMessage(UUID userId, UUID messageId);

    List<ChatMessageResponseDto> findChatHistory(UUID userId, Integer page, Integer size);

}
