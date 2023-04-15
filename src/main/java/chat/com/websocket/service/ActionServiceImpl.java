package chat.com.websocket.service;

import chat.com.websocket.dto.AbstractAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ActionServiceImpl implements ActionService {

    private final String ACTION_DESTINATION = "/async/api/action";
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ActionServiceImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Async
    @Override
    public void notifyUser(AbstractAction action) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        messagingTemplate.convertAndSend(ACTION_DESTINATION, action, headerAccessor.getMessageHeaders());
    }
}
