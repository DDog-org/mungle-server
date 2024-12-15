package ddog.domain.review.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportType {
    BAD_WORD("욕설"),
    SLANDER("비방"),
    FALSE_INFORMATION("허위정보"),
    SEXUAL_CONTENT("성적내용"),
    ADVERTISEMENT("광고"),
    ETC("기타");

    private final String keyword;
}
