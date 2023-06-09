package chat.com.authorization.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignUpRequestDto {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z\\d_-]{4,20}$")
    private String login;

    @NotNull
    private String password;
}
