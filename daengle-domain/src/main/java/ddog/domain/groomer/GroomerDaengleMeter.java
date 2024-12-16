package ddog.domain.groomer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroomerDaengleMeter {
    private Long groomerDaengleMeterId;
    private Long groomerId;
    private Integer score;
    private Long evaluationCount;

    // 새로운 리뷰에 따른 점수 업데이트
    public void updateMeterForNewReview(Integer newScore) {
        long totalScore = (long) score * evaluationCount + newScore;
        evaluationCount++;
        score = Math.toIntExact(totalScore / evaluationCount);
    }

    // 수정된 리뷰에 따른 점수 업데이트
    public void updateMeterForModifiedReview(Integer oldScore, Integer newScore) {
        long totalScore = (long) score * evaluationCount - oldScore + newScore;
        score = Math.toIntExact(totalScore / evaluationCount);
    }

    // 리뷰 삭제에 따른 점수 업데이트
    public void updateMeterForDeletedReview(Integer oldScore) {
        if (evaluationCount <= 1) {
            score = 0;
            evaluationCount = 0L;
        } else {
            long totalScore = (long) score * evaluationCount - oldScore;
            evaluationCount--;
            score = Math.toIntExact(totalScore / evaluationCount);
        }
    }
}