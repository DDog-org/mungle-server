package ddog.domain.account;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {

    DAENGLE("사용자"),
    GROOMER("미용사"),
    VET("수의사"),
    ADMIN("관리자");

    public final String description;

}
