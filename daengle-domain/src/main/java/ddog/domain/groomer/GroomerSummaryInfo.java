package ddog.domain.groomer;

import ddog.domain.groomer.enums.GroomingBadge;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GroomerSummaryInfo {
    private Long groomerAccountId;
    private String groomerName;
    private String groomerImage;
    private List<GroomingBadge> badges;
    private int daengleMeter;
    private Long reviewCount;
}