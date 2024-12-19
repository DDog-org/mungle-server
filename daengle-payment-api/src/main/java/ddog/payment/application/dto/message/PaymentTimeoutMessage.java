package ddog.payment.application.dto.message;

import ddog.domain.message.port.MessageSendable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentTimeoutMessage implements MessageSendable {
    private String paymentUid;
    private String orderUid;
    private Long estimateId;
    private long timeoutTimestamp;

    public static final String DEFAULT_GROUP = "defaultGroup";
    public static final String DEFAULT_MESSAGE_BODY_FORMAT = "Payment timeout for paymentUid: %s, orderUid: %s";

    public PaymentTimeoutMessage(String paymentUid, String orderUid, Long estimateId) {
        this.paymentUid = paymentUid;
        this.orderUid = orderUid;
        this.estimateId = estimateId;
        this.timeoutTimestamp = System.currentTimeMillis();
    }

    @Override
    public String getMessageBody() {
        return String.format(DEFAULT_MESSAGE_BODY_FORMAT, paymentUid, orderUid);
    }

    @Override
    public String getMessageGroupId() {
        return DEFAULT_GROUP;
    }

    @Override
    public String getMessageDeduplicationId() {
        return String.valueOf(System.currentTimeMillis());
    }

    @Override
    public String toString() {
        return "{" +
                "\"paymentUid\":\"" + paymentUid + "\"," +
                "\"orderUid\":\"" + orderUid + "\"," +
                "\"estimateId\":\"" + estimateId + "\"," +
                "\"timeoutTimestamp\":\"" + timeoutTimestamp + "\"" +
                "}";
    }
}