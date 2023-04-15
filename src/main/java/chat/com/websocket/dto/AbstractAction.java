package chat.com.websocket.dto;

import chat.com.websocket.enums.ActionType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public abstract class AbstractAction implements Serializable {

    protected ActionType actionType;
    protected UUID messageId;
}
