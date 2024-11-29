package ddog.pet;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Weight {

    SMALL("소형견"),
    MEDIUM("중형견"),
    LARGE("대형견");

    public final String description;
}
