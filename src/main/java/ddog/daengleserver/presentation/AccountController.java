package ddog.daengleserver.presentation;

import ddog.daengleserver.application.AccountService;
import ddog.daengleserver.global.auth.dto.PayloadDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.dto.request.UpdateNicknameDto;
import ddog.daengleserver.presentation.dto.response.AccountInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;
import static ddog.daengleserver.presentation.enums.AccountControllerResp.UPDATE_COMPLETED;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/account-info")
    public CommonResponseEntity<AccountInfoDto> getAccountInfo(PayloadDto payloadDto) {
        return success(accountService.getAccountInfo(payloadDto.getEmail(), payloadDto.getRole()));
    }

    @PostMapping("/nickname")
    public CommonResponseEntity<String> updateNickname(@RequestBody UpdateNicknameDto updateNicknameDto) {
        accountService.updateNicknameById(updateNicknameDto.getId(), updateNicknameDto.getNickname());
        return success(UPDATE_COMPLETED.getMessage());
    }
}
