package ddog.groomer.presentation.account.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProfileInfo {

    @Getter
    @Builder
    public static class UpdatePage {
        private String image;
        private String name;
        private String phoneNumber;
        private String email;
        private String introduction;
        private List<String> businessLicences;
        private List<String> licenses;
    }
}
