package chat.com.chat.service;

import chat.com.chat.dto.AddingMessageDto;
import chat.com.chat.dto.ChatMessageResponseDto;
import chat.com.chat.mapping.ChatMapping;
import chat.com.chat.repository.ChatRepository;
import chat.com.entity.Message;
import chat.com.exception.ApiException;
import chat.com.user.service.UserService;
import chat.com.websocket.dto.AbstractAction;
import chat.com.websocket.dto.MessageAddingDto;
import chat.com.websocket.dto.MessageUpdatingDto;
import chat.com.websocket.enums.ActionType;
import chat.com.websocket.service.ActionService;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final ActionService actionService;
    private final ChatRepository chatRepository;
    private final UserService userService;

    public ChatServiceImpl(ActionService actionService,
                           ChatRepository chatRepository,
                           UserService userService) {

        this.actionService = actionService;
        this.chatRepository = chatRepository;
        this.userService = userService;
    }

    @Override
    public List<ChatMessageResponseDto> findChatHistory(UUID userId, Integer page, Integer size) {
        List<Message> messages = chatRepository.findAll(PageRequest.of(page, size, Sort.by("dateTime").descending())).getContent();
        List<ChatMessageResponseDto> response = Mappers.getMapper(ChatMapping.class).map(messages, userId);
        Collections.reverse(response);
        return response;
    }

    @Override
    public Boolean sendMessage(UUID userId, AddingMessageDto request) {
        Message message = new Message();
        message.setDateTime(LocalDateTime.now());
        message.setText(request.getText());
        message.setUser(userService.findUser(userId));
        chatRepository.save(message);
        MessageAddingDto addingMessage = Mappers.getMapper(ChatMapping.class).mapToMessage(message);
        actionService.notifyUser(addingMessage);
        return true;
    }

    @Override
    public Boolean updateMessage(UUID userId, UUID messageId, AddingMessageDto request) {
        Message message = chatRepository.findById(messageId).orElseThrow(() -> new ApiException(400, "Message not Found"));
        if (!message.getUser().getUserId().equals(userId)) {
            throw new ApiException(400, "Hasn't access");
        }
        message.setText(request.getText());
        chatRepository.save(message);
        MessageUpdatingDto messageUpdating = new MessageUpdatingDto();
        messageUpdating.setMessageId(messageId);
        messageUpdating.setText(request.getText());
        actionService.notifyUser(messageUpdating);
        return true;
    }

    @Override
    public Boolean deleteMessage(UUID userId, UUID messageId) {
        chatRepository.deleteById(messageId);
        AbstractAction messageDeleting = new AbstractAction() {
            @Override
            public ActionType getActionType() {
                return ActionType.DELETE_MESSAGE;
            }
        };
        messageDeleting.setMessageId(messageId);
        actionService.notifyUser(messageDeleting);
        return true;
    }
}
