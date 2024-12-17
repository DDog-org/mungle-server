package ddog.domain.vet.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CareKeyword {
    KIND("친절해요", null),
    PROFESSIONAL("전문적이에요", null),
    GOOD_AT_EXPLAINING("설명을 잘해줘요", null),
    EXCELLENT_CONSULTATION("상담을 잘해줘요", null),
    REASONABLE("가격이 적당해요", null),
    HYGIENIC("위생적이에요", null),
    FOR_OLD_DOGS("노견을 잘 다뤄요", CareBadge.OLD_DOG),
    FOR_LARGE_DOGS("대형견을 잘 다뤄요", CareBadge.LARGE_DOG),
    GOOD_WITH_DANGEROUS_DOGS("맹견을 잘 다뤄요", CareBadge.DANGEROUS_DOG),
    CARES_FOR_SKIN_DISEASE("피부병 케어를 잘해요", CareBadge.SKIN_DISEASE),
    HEART_DISEASE_CARE("심장질환 케어를 잘해요", CareBadge.HEART_DISEASE);

    public final String keyword;
    public final CareBadge badge;
}
