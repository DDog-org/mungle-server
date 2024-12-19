package ddog.payment.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ddog.payment.application.dto.message.PaymentTimeoutMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.sqs.model.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTimeOutMessageListener {

    private final ObjectMapper objectMapper;
    private final PaymentService paymentService;

    @SqsListener(value = "PaymentTimeoutQ.fifo", factory = "sqsListenerContainerFactory")
    public void listen(Message message) {
        try {
            PaymentTimeoutMessage paymentTimeoutMessage = parseMessageBody(message);
            paymentService.refundPayment(paymentTimeoutMessage.getPaymentUid());

            log.info("Refund processed successfully for paymentUid: {}", paymentTimeoutMessage.getPaymentUid());
        } catch (Exception e) {
            log.error("Failed to process message: {}", message, e);
        }
    }

    private PaymentTimeoutMessage parseMessageBody(Message message) {
        try {
            JsonNode bodyNode = objectMapper.readTree(message.body());

            String paymentUid = bodyNode.get("paymentUid").asText();
            String orderUid = bodyNode.get("orderUid").asText();
            Long estimateId = Long.valueOf(bodyNode.get("estimateId").asText());

            return PaymentTimeoutMessage.builder()
                    .paymentUid(paymentUid)
                    .orderUid(orderUid)
                    .estimateId(estimateId)
                    .build();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse message: " + message, e);
        }
    }
}