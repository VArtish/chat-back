package chat.com.authorization.service;

import chat.com.authorization.dto.JwtRequestDto;
import chat.com.authorization.dto.JwtResponseDto;
import chat.com.authorization.dto.SignUpRequestDto;
import lombok.NonNull;

public interface AuthService {

    JwtResponseDto login(@NonNull JwtRequestDto authRequest);

    JwtResponseDto refresh(@NonNull String refreshToken);

    JwtResponseDto signUp(SignUpRequestDto request);
}
