package ddog.groomer.presentation.account;

import ddog.groomer.application.AccountService;
import ddog.groomer.application.exception.common.CommonResponseEntity;
import ddog.groomer.presentation.account.dto.SignUpReq;
import ddog.groomer.presentation.account.dto.SignUpResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ddog.groomer.application.exception.common.CommonResponseEntity.*;

@RestController
@RequestMapping("/api/groomer")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/join")
    public CommonResponseEntity<SignUpResp> signUp(@RequestBody SignUpReq request) {
        return success(accountService.signUp(request));
    }

}
