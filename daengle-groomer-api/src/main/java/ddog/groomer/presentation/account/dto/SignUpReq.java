package ddog.groomer.presentation.account.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SignUpReq {
    private String email;

    private String name;
    private String phoneNumber;
    private String shopName;
    private String address;
    private List<String> businessLicenses;
    private List<String> licenses;
}
