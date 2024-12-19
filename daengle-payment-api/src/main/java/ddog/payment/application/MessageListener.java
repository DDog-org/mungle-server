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
public class MessageListener {

    private final ObjectMapper objectMapper;

    @SqsListener(value = "PaymentTimeoutQ.fifo", factory = "sqsListenerContainerFactory")
    public void listen(Message message) {
        System.out.println("Received message: " + message);

        PaymentTimeoutMessage paymentTimeoutMessage =  parseMessageBody(message);
        // 메시지 처리 로직

        log.info(paymentTimeoutMessage.getMessageBody() +" SUCCESS");
    }

    private PaymentTimeoutMessage parseMessageBody(Message message) {
        try {
            // ObjectMapper로 JSON 메시지를 파싱
            JsonNode bodyNode = objectMapper.readTree(message.body());
            log.info("Parsed message body: {}", bodyNode);

            // 필드 값 추출
            String paymentUid = bodyNode.get("paymentUid").asText();
            String orderUid = bodyNode.get("orderUid").asText();
            Long estimateId = Long.valueOf(bodyNode.get("estimateId").asText());

            // 결과 객체 생성
            return PaymentTimeoutMessage.builder()
                    .paymentUid(paymentUid)
                    .orderUid(orderUid)
                    .estimateId(estimateId)
                    .build();
        } catch (Exception e) {
            // 에러 로그 출력 및 IllegalArgumentException 던지기
            log.error("Failed to parse message: {}", message, e);
            throw new IllegalArgumentException("Failed to parse message: " + message, e);
        }
    }
}