package ddog.domain.groomer;

import ddog.domain.groomer.enums.GroomingKeyword;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GroomerSummaryInfo {
    private Long groomerAccountId;
    private String groomerName;
    private String groomerImage;
    private List<GroomingKeyword> keywords;
    private int daengleMeter;
    private Long reviewCount;
}