package ddog.user.presentation.estimate.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ddog.domain.estimate.Proposal;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserEstimate {

    @Getter
    @Builder
    public static class Grooming {
        public Long id;
        public String address;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        public LocalDateTime reservedDate;
        public Proposal proposal;
        public String petImage;
        public String petName;
        public String desiredStyle;
        public String requirements;
    }

    @Getter
    @Builder
    public static class Care {
        public Long id;
        public String address;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        public LocalDateTime reservedDate;
        public Proposal proposal;
        public String petImage;
        public String petName;
        public String symptoms;
        public String requirements;

    }
}
