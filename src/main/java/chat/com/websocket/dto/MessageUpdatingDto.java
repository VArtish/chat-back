package chat.com.websocket.dto;

import chat.com.websocket.enums.ActionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageUpdatingDto extends AbstractAction {

    public MessageUpdatingDto() {
        this.actionType = ActionType.UPDATE_MESSAGE;
    }
    private String text;
}
