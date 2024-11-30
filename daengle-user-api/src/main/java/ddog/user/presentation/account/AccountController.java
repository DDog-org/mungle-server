package ddog.user.presentation.account;

import ddog.auth.dto.PayloadDto;
import ddog.user.presentation.account.dto.UserInfo;
import ddog.user.presentation.account.dto.*;
import ddog.user.presentation.account.dto.BreedInfo;
import ddog.user.presentation.account.dto.PetInfo;
import ddog.user.presentation.account.dto.UserProfileInfo;
import ddog.user.application.AccountService;
import ddog.user.application.exception.common.CommonResponseEntity;
import ddog.user.presentation.account.dto.ValidResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.user.application.exception.common.CommonResponseEntity.success;
import static ddog.user.presentation.account.AccountControllerResp.*;


@RestController
@RequestMapping("/api/daengle")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/available-nickname")
    public CommonResponseEntity<ValidResponse.Nickname> hasNickname(@RequestBody CheckNicknameReq request) {
        return success(accountService.hasNickname(request.getNickname()));
    }

    @GetMapping("/breed-list")
    public CommonResponseEntity<BreedInfo> getBreedList() {
        return success(accountService.getBreedInfos());
    }

    @PostMapping("/join-with-pet")
    public CommonResponseEntity<String> joinUserWithPet(@RequestBody JoinUserWithPet request) {
        accountService.createUserWithPet(request);
        return success(USER_JOIN_COMPLETED.getMessage());
    }

    @PostMapping("/join-without-pet")
    public CommonResponseEntity<String> joinUserWithoutPet(@RequestBody JoinUserWithoutPet request) {
        accountService.createUserWithoutPet(request);
        return success(USER_JOIN_COMPLETED.getMessage());
    }

    @GetMapping("/modify-page")
    public CommonResponseEntity<UserProfileInfo> getUserProfileInfo(PayloadDto payloadDto) {
        return success(accountService.getUserProfileInfo(payloadDto.getAccountId()));
    }

    @PostMapping("/modify-info")
    public CommonResponseEntity<String> modifyUserProfile(@RequestBody UserProfileModifyReq request, PayloadDto payloadDto) {
        accountService.modifyUserProfile(request, payloadDto.getAccountId());
        return success(PROFILE_MODIFY_COMPLETED.getMessage());
    }

    @PostMapping("/add-pet")
    public CommonResponseEntity<String> addPet(@RequestBody AddPetInfo request, PayloadDto payloadDto) {
        accountService.addPet(request, payloadDto.getAccountId());
        return success(PET_ADD_COMPLETED.getMessage());
    }

    @GetMapping("/pets-info")
    public CommonResponseEntity<PetInfo> getPetInfo(PayloadDto payloadDto) {
        return success(accountService.getPetInfo(payloadDto.getAccountId()));
    }

    @PostMapping("/modify-pet")
    public CommonResponseEntity<String> modifyPetProfile(@RequestBody ModifyPetInfo request, PayloadDto payloadDto) {
        accountService.modifyPetProfile(request, payloadDto.getAccountId());
        return success(PET_PROFILE_MODIFY_COMPLETED.getMessage());
    }

    @DeleteMapping("/delete-pet")
    public CommonResponseEntity<String> deletePet(@RequestBody DeletePetId request) {
        accountService.deletePet(request.getPetId());
        return success(DELETE_PET_COMPLETED.getMessage());
    }

    @GetMapping("/user-pets-info")
    public CommonResponseEntity<UserInfo> getUserAndPetInfos(PayloadDto payloadDto) {
        return success(accountService.getUserAndPetInfos(payloadDto.getAccountId()));
    }
}
