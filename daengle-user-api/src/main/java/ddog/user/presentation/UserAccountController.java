package ddog.user.presentation;

import ddog.auth.dto.PayloadDto;
import ddog.estimate.dto.response.UserInfo;
import ddog.user.application.UserAccountService;
import ddog.user.dto.request.*;
import ddog.user.dto.response.BreedInfo;
import ddog.user.dto.response.PetInfo;
import ddog.user.dto.response.UserProfileInfo;
import ddog.user.exception.common.CommonResponseEntity;
import ddog.user.presentation.enums.UserAccountControllerResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.user.exception.common.CommonResponseEntity.success;


@RestController
@RequestMapping("/api/daengle")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;

    @PostMapping("/available-nickname")
    public CommonResponseEntity<Boolean> hasNickname(@RequestBody CheckNicknameReq request) {
        return success(userAccountService.hasNickname(request.getNickname()));
    }

    @GetMapping("/breed-list")
    public CommonResponseEntity<BreedInfo> getBreedList() {
        return success(userAccountService.getBreedInfos());
    }

    @PostMapping("/join-with-pet")
    public CommonResponseEntity<String> joinUserWithPet(@RequestBody JoinUserWithPet request) {
        userAccountService.createUserWithPet(request);
        return success(UserAccountControllerResp.USER_JOIN_COMPLETED.getMessage());
    }

    @PostMapping("/join-without-pet")
    public CommonResponseEntity<String> joinUserWithoutPet(@RequestBody JoinUserWithoutPet request) {
        userAccountService.createUserWithoutPet(request);
        return success(UserAccountControllerResp.USER_JOIN_COMPLETED.getMessage());
    }

    @GetMapping("/modify-page")
    public CommonResponseEntity<UserProfileInfo> getUserProfileInfo(PayloadDto payloadDto) {
        return success(userAccountService.getUserProfileInfo(payloadDto.getAccountId()));
    }

    @PostMapping("/modify-info")
    public CommonResponseEntity<String> modifyUserProfile(@RequestBody UserProfileModifyReq request, PayloadDto payloadDto) {
        userAccountService.modifyUserProfile(request, payloadDto.getAccountId());
        return success(UserAccountControllerResp.PROFILE_MODIFY_COMPLETED.getMessage());
    }

    @PostMapping("/add-pet")
    public CommonResponseEntity<String> addPet(@RequestBody AddPetInfo request, PayloadDto payloadDto) {
        userAccountService.addPet(request, payloadDto.getAccountId());
        return success(UserAccountControllerResp.PET_ADD_COMPLETED.getMessage());
    }

    @GetMapping("/pets-info")
    public CommonResponseEntity<PetInfo> getPetInfo(PayloadDto payloadDto) {
        return success(userAccountService.getPetInfo(payloadDto.getAccountId()));
    }

    @PostMapping("/modify-pet")
    public CommonResponseEntity<String> modifyPetProfile(@RequestBody ModifyPetInfo request, PayloadDto payloadDto) {
        userAccountService.modifyPetProfile(request, payloadDto.getAccountId());
        return success(UserAccountControllerResp.PET_PROFILE_MODIFY_COMPLETED.getMessage());
    }

    @DeleteMapping("/delete-pet")
    public CommonResponseEntity<String> deletePet(@RequestBody DeletePetId request) {
        userAccountService.deletePet(request.getPetId());
        return success(UserAccountControllerResp.DELETE_PET_COMPLETED.getMessage());
    }

    @GetMapping("/user-pets-info")
    public CommonResponseEntity<UserInfo> getUserAndPetInfos(PayloadDto payloadDto) {
        return success(userAccountService.getUserAndPetInfos(payloadDto.getAccountId()));
    }
}
