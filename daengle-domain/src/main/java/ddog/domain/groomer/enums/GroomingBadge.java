package ddog.domain.groomer.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GroomingBadge {

    OLD_DOG("#노견"),
    LARGE_DOG("#대형견"),
    DANGEROUS_DOG("#맹견"),
    SKIN_DISEASE("#피부병");

    private String title;

}
