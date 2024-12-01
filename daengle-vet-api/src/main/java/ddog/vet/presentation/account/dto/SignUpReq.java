package ddog.vet.presentation.account.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SignUpReq {
    private String email;

    private String name;
    private String address;
    private String detailAddress;
    private String phoneNumber;
    private List<String> licenses;
}
