package ddog.domain.message.port;

public interface MessageSendable {
    String getMessageBody();
    String getMessageGroupId();
    String getMessageDeduplicationId();
}
