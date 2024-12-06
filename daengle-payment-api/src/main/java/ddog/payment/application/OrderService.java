package ddog.payment.application;

import ddog.domain.payment.Order;
import ddog.domain.payment.dto.PostOrderInfo;
import ddog.domain.payment.Payment;
import ddog.domain.payment.enums.ServiceType;
import ddog.payment.application.dto.response.PostOrderResp;
import ddog.persistence.mysql.port.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderPersist orderPersist;
    private final PaymentPersist paymentPersist;
    private final GroomingEstimatePersist groomingEstimatePersist;
    private final CareEstimatePersist careEstimatePersist;

    @Transactional
    public PostOrderResp processOrder(Long accountId, PostOrderInfo postOrderInfo) {
        validateEstimate(postOrderInfo.getServiceType(), postOrderInfo.getEstimateId());

        Payment paymentToSave = Payment.createTemporaryHistoryBy(accountId, postOrderInfo);
        Payment SavedPayment = paymentPersist.save(paymentToSave);

        Order order = Order.createBy(accountId, postOrderInfo, SavedPayment);
        orderPersist.save(order);

        return PostOrderResp.builder()
                .accountId(accountId)
                .estimateId(postOrderInfo.getEstimateId())
                .orderUId(order.getOrderUid())
                .build();
    }

    private void validateEstimate(ServiceType serviceType, Long estimateId) {
//        switch (serviceType) {
//            case GROOMING:
//                if (groomingEstimatePersist.getByGroomingEstimateId(estimateId) == null) {
//                    throw new IllegalArgumentException("Invalid estimate ID for Grooming service.");
//                }
//                break;
//
//            case CARE:
//                if (careEstimatePersist.getByCareEstimateId(estimateId) == null) {
//                    throw new IllegalArgumentException("Invalid estimate ID for Care service.");
//                }
//                break;
//
//            default:
//                throw new IllegalArgumentException("Unsupported service type: " + serviceType);
//        }
    }
}
