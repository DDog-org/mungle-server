package ddog.daengleserver.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {

    CUSTOMER("사용자"),
    GROOMER("미용사"),
    ADMIN("관리자");

    public final String description;

}
