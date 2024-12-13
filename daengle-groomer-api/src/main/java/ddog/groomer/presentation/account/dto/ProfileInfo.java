package ddog.groomer.presentation.account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ProfileInfo {

    @Getter
    @Builder
    public static class UpdatePage {
        private String image;
        private String instagramId;
        private String name;
        private String phoneNumber;
        private String email;
        private String introduction;
        private List<LicenseDetail> licenses;

        @Getter
        @Builder
        public static class LicenseDetail {
            private String name;

            @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
            private LocalDate acquisitionDate;
        }
    }
}
