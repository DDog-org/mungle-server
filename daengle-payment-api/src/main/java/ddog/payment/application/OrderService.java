package ddog.payment.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.domain.estimate.port.GroomingEstimatePersist;
import ddog.domain.payment.Order;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.payment.application.exception.*;
import ddog.payment.application.mapper.OrderMapper;
import ddog.payment.application.mapper.PaymentMapper;
import ddog.payment.presentation.dto.PostOrderInfo;
import ddog.domain.payment.Payment;
import ddog.domain.payment.enums.ServiceType;
import ddog.domain.payment.port.OrderPersist;
import ddog.domain.payment.port.PaymentPersist;
import ddog.payment.application.dto.response.PostOrderResp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderPersist orderPersist;
    private final PaymentPersist paymentPersist;

    private final UserPersist userPersist;

    private final GroomingEstimatePersist groomingEstimatePersist;
    private final CareEstimatePersist careEstimatePersist;

    @Transactional
    public PostOrderResp processOrder(String idempotencyKey, Long accountId, PostOrderInfo postOrderInfo) {
        try {
            orderPersist.findByIdempotencyKey(idempotencyKey) //멱등성 보장
                    .ifPresent(order -> {
                        throw new OrderException(OrderExceptionType.ORDER_ALREADY_PROCESSED);
                    });

            User savedUser = userPersist.findByAccountId(accountId)
                    .orElseThrow(() -> new OrderException(OrderExceptionType.ORDER_USER_NOT_FOUNDED));

            validateEstimate(postOrderInfo.getServiceType(), postOrderInfo.getEstimateId());
            validatePostOrderInfoDataFormat(postOrderInfo);

            Payment paymentToSave = PaymentMapper.createTemporaryHistoryBy(savedUser.getAccountId(), postOrderInfo);
            Payment SavedPayment = paymentPersist.save(paymentToSave);

            Order orderToSave = OrderMapper.createBy(idempotencyKey, savedUser, postOrderInfo, SavedPayment);
            Order savedOrder = orderPersist.save(orderToSave);

            return PostOrderResp.builder()
                    .orderId(savedOrder.getOrderId())
                    .accountId(accountId)
                    .estimateId(postOrderInfo.getEstimateId())
                    .orderUId(savedOrder.getOrderUid())
                    .build();

        } catch (DataIntegrityViolationException e) {  //데이터 무결성 제약조건 위배
            log.info(e.getMessage());
            throw new OrderException(OrderExceptionType.ORDER_ALREADY_PROCESSED);
        }
    }

    //TODO 서비스 제공자(미용사/수의사) PK 유요한지 검증, Groomer || Vet 도메인 객체에게 역할 위임
    private void validatePostOrderInfoDataFormat(PostOrderInfo postOrderInfo) {
        Order.validateRecipientName(postOrderInfo.getRecipientName());
        Order.validateShopName(postOrderInfo.getShopName());
        Order.validateSchedule(postOrderInfo.getSchedule());
        Order.validatePrice(postOrderInfo.getPrice());
        Order.validatePhoneNumber(postOrderInfo.getVisitorPhoneNumber());
    }

    //TODO Estimate 도메인에게 역할 위임하기, 확장성이 있는 유효성 검사 로직을 구현하기 (언제든 새로운 00견적 서비스가 추가될 수 있다)
    private void validateEstimate(ServiceType serviceType, Long estimateId) {
        if (serviceType.equals(ServiceType.GROOMING)) {
            GroomingEstimate estimate = groomingEstimatePersist.findByEstimateId(estimateId)
                    .orElseThrow(() -> new GroomingEstimateException(GroomingEstimateExceptionType.GROOMING_ESTIMATE_NOT_FOUND));

            groomingEstimatePersist.updateStatusWithParentId(EstimateStatus.END, estimate.getParentId());

            estimate.updateStatus(EstimateStatus.ON_RESERVATION);
            groomingEstimatePersist.save(estimate);

        } else if (serviceType.equals(ServiceType.CARE)) {
            CareEstimate estimate = careEstimatePersist.findByEstimateId(estimateId)
                    .orElseThrow(() -> new CareEstimateException(CareEstimateExceptionType.CARE_ESTIMATE_NOT_FOUND));

            careEstimatePersist.updateStatusWithParentId(EstimateStatus.END, estimate.getParentId());

            estimate.updateStatus(EstimateStatus.ON_RESERVATION);
            careEstimatePersist.save(estimate);

        } else {
            throw new IllegalArgumentException("서비스 타입이 올바르지 않습니다.");
        }
    }
}
