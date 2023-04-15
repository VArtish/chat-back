package chat.com.websocket.service;

import chat.com.websocket.dto.AbstractAction;

import java.util.Collection;
import java.util.UUID;

public interface ActionService {

    void notifyUser(AbstractAction action);
}
