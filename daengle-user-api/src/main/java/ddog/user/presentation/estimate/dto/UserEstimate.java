package ddog.user.presentation.estimate.dto;

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
        public LocalDateTime reservedDate;
        public Proposal proposal;
        public String petImage;
        public String petName;
        public String symptoms;
        public String requirements;

    }
}
