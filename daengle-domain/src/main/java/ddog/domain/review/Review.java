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
    private Long reservationId;
    private Long reviewerId;
    private String revieweeName;
    private String shopName;
    private Long starRating;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime modifiedTime;
    private List<String> imageUrlList;

    public static void validateStarRating(Long starRating) {
        if (starRating == null || starRating < 1 || starRating > 5) {
            throw new IllegalArgumentException("Star rating must be between 1 and 5.");
        }
    }

    public static void validateContent(String content) {
        if (content != null && content.length() > 400) {
            throw new IllegalArgumentException("Content must be 400 characters or less.");
        }
    }

    public static void validateImageUrlList(List<String> imageUrlList) {
        if (imageUrlList != null && imageUrlList.size() > 10) {
            throw new IllegalArgumentException("Image URL list must contain 10 items or less.");
        }
    }
}
