package ddog.domain.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Part {
    NONE("없음"),
    EYE("눈"),
    NOSE("코"),
    MOUTH("입"),
    EAR("귀"),
    NECK("목"),
    BODY("몸통"),
    LEG("다리"),
    TAIL("꼬리"),
    GENITAL("생식기");

    private String name;
}
