package ddog.daengleserver.domain.estimate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Proposal {
    GENERAL("일반"),
    DESIGNATION("지정");

    public String description;
}
