package ddog.domain.review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Review {
    private Long reservationId;
    private Long reviewerId;
    private Long revieweeId;
    private Long starRating;
    private String content;
    private List<String> imageUrlList;
}
