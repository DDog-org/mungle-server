package ddog.daengleserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vet {

    private Long vetId;
    private Long accountId;
    private int daengleMeter;
    private String vetName;
    private String vetImage;
    private String address;
    private String vetIntroduction;
}
