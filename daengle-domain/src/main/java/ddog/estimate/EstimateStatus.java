package ddog.estimate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum EstimateStatus {
    NEW("신규"),
    PENDING("대기중"),
    ON_RESERVATION("예약중"),
    STOP_PROGRESS("진행 중지"),
    COMPLETED("완료"),
    DELETED("삭제");

    public String description;
}
