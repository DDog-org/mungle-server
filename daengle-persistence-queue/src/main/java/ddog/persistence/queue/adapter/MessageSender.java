package ddog.persistence.queue.adapter;

import ddog.domain.message.port.MessageSend;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageSender implements MessageSend {

    public static final String SSQ_NAME = "PaymentTimeoutQ.fifo";
    private final SqsTemplate sqsTemplate;


    @Override
    public void send(String message) {
        Message<String> newMessage = MessageBuilder.withPayload(message)
                .setHeader("message-group-id", "defaultGroup")
                .setHeader("message-deduplication-id", String.valueOf(System.currentTimeMillis()))
                .build();

        log.info("Sending message: {}", newMessage);

        sqsTemplate.sendAsync(SSQ_NAME, newMessage)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send message", ex);
                    } else {
                        log.info("Message sent successfully, result: {}", result);
                    }
                });
    }
}
