package ddog.payment.application;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @SqsListener(value = "PaymentTimeoutQ.fifo", factory = "sqsListenerContainerFactory")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        // 메시지 처리 로직
    }
}