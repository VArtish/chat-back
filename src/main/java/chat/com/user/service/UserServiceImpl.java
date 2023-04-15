package chat.com.user.service;

import chat.com.entity.User;
import chat.com.exception.user.UserNotFoundApiException;
import chat.com.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UUID> findAll() {
        return userRepository.findAll().stream().map(User::getUserId).collect(Collectors.toList());
    }

    @Override
    public User findUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundApiException::new);
    }
}
