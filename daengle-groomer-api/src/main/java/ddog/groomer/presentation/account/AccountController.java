package ddog.groomer.presentation.account;

import ddog.auth.dto.PayloadDto;
import ddog.groomer.application.AccountService;
import ddog.groomer.application.exception.common.CommonResponseEntity;
import ddog.groomer.presentation.account.dto.ModifyInfoReq;
import ddog.groomer.presentation.account.dto.ProfileInfo;
import ddog.groomer.presentation.account.dto.SignUpReq;
import ddog.groomer.presentation.account.dto.SignUpResp;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.groomer.application.exception.common.CommonResponseEntity.*;

@RestController
@RequestMapping("/api/groomer")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/join")
    public CommonResponseEntity<SignUpResp> signUp(@RequestBody SignUpReq request, HttpServletResponse response) {
        return success(accountService.signUp(request, response));
    }

    @GetMapping("/modify-page")
    public CommonResponseEntity<ProfileInfo.ModifyPage> getModifyInfo(PayloadDto payloadDto) {
        return success(accountService.getModifyPage(payloadDto.getAccountId()));
    }

    @PatchMapping("/profile")
    public CommonResponseEntity<String> modifyInfo(@RequestBody ModifyInfoReq request, PayloadDto payloadDto) {
        accountService.modifyInfo(request, payloadDto.getAccountId());
        return success("미용사 페이지가 정상적으로 수정됐습니다.");
    }
}
