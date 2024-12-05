package ddog.domain.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Review {
    private Long reviewId;
    private Long reviewerId;
    private Long revieweeId;
    private Long reviewCount;
    private Long starRating;
    private String content;
    private LocalDateTime createTime;
    private List<String> imageUrlList;
}
