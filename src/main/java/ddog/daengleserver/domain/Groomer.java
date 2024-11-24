package ddog.daengleserver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Groomer {

    private Long groomerId;
    private Long accountId;
    private String groomerName;
    private String groomerImage;
    private String address;
    private String shopName;
    private String groomerIntroduction;
}
