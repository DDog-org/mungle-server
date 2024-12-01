package ddog.domain.groomer;

import ddog.domain.account.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Groomer {

    private Long groomerId;
    private Long accountId;
    private Status status;
    private int daengleMeter;
    private String groomerName;
    private String phoneNumber;
    private String groomerImage;
    private String email;
    private String address;
    private String detailAddress;
    private String shopName;
    private String groomerIntroduction;
    private List<String> businessLicenses;
    private List<String> licenses;

}
