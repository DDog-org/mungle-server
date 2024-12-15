package ddog.domain.chat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PartnerType {
    VET_PARTNER("병원"),
    GROOMER_PARTNER("미용사");

    private String message;
}
