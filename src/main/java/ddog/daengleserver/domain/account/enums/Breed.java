package ddog.daengleserver.domain.account.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Breed {
    MALTESE("말티즈"),
    POODLE("푸들"),
    GOLDEN_RETRIEVER("골든 리트리버");

    private String name;
}
