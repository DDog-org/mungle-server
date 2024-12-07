package ddog.domain.estimate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EstimateStatus {
    NEW("신규"),
    PENDING("대기중"),
    ON_RESERVATION("예약중"),
    END("종료");

    public String description;
}
