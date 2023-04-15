package chat.com.user.service;

import chat.com.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UUID> findAll();

    User findUser(UUID userId);
}
