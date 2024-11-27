package ddog.daengleserver.presentation.account;

import ddog.daengleserver.application.UserAccountService;
import ddog.daengleserver.global.auth.dto.PayloadDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.account.dto.request.CheckNicknameReq;
import ddog.daengleserver.presentation.account.dto.request.JoinUserWithPet;
import ddog.daengleserver.presentation.account.dto.request.JoinUserWithoutPet;
import ddog.daengleserver.presentation.account.dto.request.UserProfileModifyReq;
import ddog.daengleserver.presentation.account.dto.response.UserProfileInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;
import static ddog.daengleserver.presentation.account.enums.UserAccountControllerResp.PROFILE_MODIFY_COMPLETED;
import static ddog.daengleserver.presentation.account.enums.UserAccountControllerResp.USER_JOIN_COMPLETED;

@RestController
@RequestMapping("/api/daengle")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/available-nickname")
    public CommonResponseEntity<Boolean> hasNickname(@RequestBody CheckNicknameReq request) {
        return success(userAccountService.hasNickname(request.getNickname()));
    }

    @PostMapping("/join-without-pet")
    public CommonResponseEntity<String> joinUserWithoutPet(@RequestBody JoinUserWithoutPet request) {
        userAccountService.createUserWithoutPet(request);
        return success(USER_JOIN_COMPLETED.getMessage());
    }

    @PostMapping("/join-with-pet")
    public CommonResponseEntity<String> joinUserWithPet(@RequestBody JoinUserWithPet request) {
        userAccountService.createUserWithPet(request);
        return success(USER_JOIN_COMPLETED.getMessage());
    }

    @GetMapping("/modify-page")
    public CommonResponseEntity<UserProfileInfo> getUserProfileInfo(PayloadDto payloadDto) {
        return success(userAccountService.getUserProfileInfo(payloadDto.getAccountId()));
    }

    @PostMapping("/modify-info")
    public CommonResponseEntity<String> modifyUserProfile(@RequestBody UserProfileModifyReq request, PayloadDto payloadDto) {
        userAccountService.modifyUserProfile(request, payloadDto.getAccountId());
        return success(PROFILE_MODIFY_COMPLETED.getMessage());
    }
}
