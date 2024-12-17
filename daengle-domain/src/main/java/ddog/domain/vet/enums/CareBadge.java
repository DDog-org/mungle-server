package ddog.domain.vet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CareBadge {

    OLD_DOG("#노견"),
    LARGE_DOG("#대형견"),
    DANGEROUS_DOG("#맹견"),
    SKIN_DISEASE("#피부병"),
    HEART_DISEASE("#심장질환");

    private final String badge;

}
