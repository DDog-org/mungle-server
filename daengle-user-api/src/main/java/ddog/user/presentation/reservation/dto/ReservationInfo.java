package ddog.user.presentation.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReservationInfo {

    @Getter
    @Builder
    public static class Grooming {

        private List<Content> contents;

        @Getter
        @Builder
        public static class Content {
            private Long reservationId;
            private Long estimateId;
            private String groomerName;
            private String petName;
            private String petImageUrl;
            private String shopName;

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            private LocalDateTime reservedDate;
        }
    }

    @Getter
    @Builder
    public static class Care {

        private List<Content> contents;

        @Getter
        @Builder
        public static class Content {
            private Long reservationId;
            private Long estimateId;
            private String vetName;
            private String petName;
            private String petImageUrl;

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
            private LocalDateTime reservedDate;
        }
    }

    @Getter
    @Builder
    public static class ReservationUsersInfo {
        private Long estimateId;
        private Long partnerId;
        private String partnerPhone;
        private String partnerName;
        private Long userId;
        private String userName;

    }
}
