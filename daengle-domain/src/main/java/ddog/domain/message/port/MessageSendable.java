package ddog.domain.message;

public interface MessageSendable {
    String getMessageBody();
    String getMessageGroupId();
    String getMessageDeduplicationId();
}
