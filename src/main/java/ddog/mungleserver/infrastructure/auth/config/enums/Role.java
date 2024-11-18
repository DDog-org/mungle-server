package ddog.mungleserver.infrastructure.auth.config.enums;

public enum Role {

    CUSTOMER("사용자"),
    GROOMER("미용사"),
    ADMIN("관리자");

    private String description;

    Role(String description) {
        this.description = description;
    }
}
