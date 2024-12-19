package ddog.payment.application;


import ddog.payment.application.dto.message.PaymentTimeoutMessage;
import ddog.payment.application.exception.PaymentException;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTimeoutScheduler {

    private final PaymentService paymentService;
    private final SqsTemplate sqsTemplate;

    private static final String SQS_QUEUE_NAME = "PaymentTimeoutQ.fifo";

    // 매 30분마다 큐에서 타임아웃 메시지를 처리
    @Scheduled(fixedRate = 1800000)
    public void handlePaymentTimeouts() {
//        List<PaymentTimeoutMessage> timeoutMessages = receiveMessagesFromQueue();
//
//        timeoutMessages.forEach(message -> {
//            try {
//                paymentService.refundPayment(message.getPaymentUid());  // 결제 환불 처리
//            } catch (PaymentException e) {
//                // 환불 처리 실패 시 로깅 또는 추가 작업
//                log.error("Failed to process refund for paymentUid: {}", message.getPaymentUid(), e);
//            }
//        });

    }
/*
    private List<PaymentTimeoutMessage> receiveMessagesFromQueue() {
        // SQS에서 메시지 가져오기
        List<Message<String>> messages = sqsTemplate.receive(SQS_QUEUE_NAME, 10); // 최대 10개 메시지 가져오기

        // 메시지가 없으면 빈 리스트 반환
        if (messages == null || messages.isEmpty()) {
            return List.of();
        }

        // 메시지를 PaymentTimeoutMessage 객체로 변환
        return messages.stream()
                .map(message -> {
                    // 여기서는 payload에 있는 정보를 바탕으로 PaymentTimeoutMessage 객체를 생성합니다.
                    // 예시로는 단순히 payload가 메시지 본문이므로 이를 사용합니다.
                    String payload = message.getPayload();
                    String[] payloadParts = payload.split(","); // 간단한 처리, 실제로는 JSON 등을 파싱해야 할 수도 있습니다.

                    String paymentUid = payloadParts[0].split(":")[1].trim(); // paymentUid 추출
                    String orderUid = payloadParts[1].split(":")[1].trim();  // orderUid 추출
                    String accountId = payloadParts[2].split(":")[1].trim(); // accountId 추출

                    return new PaymentTimeoutMessage(paymentUid, orderUid, accountId); // PaymentTimeoutMessage 생성
                })
                .collect(Collectors.toList());
    }*/
}