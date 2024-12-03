package ddog.domain.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SignificantTag {

    SKIN_DISEASES("피부병"),
    HEART_DISEASE("심장질환"),
    MARKING("마킹"),
    MOUNTING("마운팅");

    private String name;
}
