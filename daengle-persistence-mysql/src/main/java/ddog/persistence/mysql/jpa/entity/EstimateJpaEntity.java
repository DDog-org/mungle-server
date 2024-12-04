package ddog.persistence.jpa.entity;

import ddog.domain.estimate.Estimate;
import ddog.domain.estimate.EstimateStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Estimates")
public class EstimateJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long estimateId;
    private EstimateStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static EstimateJpaEntity from(Estimate estimate) {
        return EstimateJpaEntity.builder()
                .estimateId(estimate.getEstimateId())
                .status(estimate.getStatus())
                .createdAt(estimate.getCreatedAt())
                .updatedAt(estimate.getUpdatedAt())
                .build();
    }

    public Estimate toModel() {
        return Estimate.builder()
                .estimateId(estimateId)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
