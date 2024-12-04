package ddog.notification.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public boolean isUserLoggedIn(Long userId) {
        return redisTemplate.hasKey("user_loggedIn:" + userId);
    }

    public void loginUser(Long userId) {
        redisTemplate.opsForValue().set("user_loggedIn:" + userId, "true");
    }

    public void logoutUser(Long userId) {
        redisTemplate.delete("user_loggedIn:" + userId);
    }
}
