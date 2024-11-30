package ddog.notification.application;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class RedisMessageListener implements MessageListener {
    private final SseEmitterService sseEmitterService;

    public RedisMessageListener(SseEmitterService sseEmitterService) {
        this.sseEmitterService = sseEmitterService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        String msg = message.toString();

        if ("all_users".equals(channel)) {
            sseEmitterService.sendMessageToAllUsers(msg);
        } else if (channel.startsWith("user_logged_in:")) {
            Long userId = Long.valueOf(channel.substring("user_logged_in:".length()));
            sseEmitterService.sendMessageToUser(userId, msg);
        }
    }
}
