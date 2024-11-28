package ddog.daengleserver.presentation.account;

import ddog.daengleserver.application.UserAccountService;
import ddog.daengleserver.global.auth.dto.PayloadDto;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.account.dto.request.*;
import ddog.daengleserver.presentation.account.dto.response.BreedInfo;
import ddog.daengleserver.presentation.account.dto.response.PetInfos;
import ddog.daengleserver.presentation.account.dto.response.UserProfileInfo;
import ddog.daengleserver.presentation.estimate.dto.response.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;
import static ddog.daengleserver.presentation.account.enums.UserAccountControllerResp.*;

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
        return success(USER_JOIN_COMPLETED.getMessage());
    }

    @PostMapping("/join-without-pet")
    public CommonResponseEntity<String> joinUserWithoutPet(@RequestBody JoinUserWithoutPet request) {
        userAccountService.createUserWithoutPet(request);
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

    @PostMapping("/add-pet")
    public CommonResponseEntity<String> addPet(@RequestBody AddPetInfo request, PayloadDto payloadDto) {
        userAccountService.addPet(request, payloadDto.getAccountId());
        return success(PET_ADD_COMPLETED.getMessage());
    }

    @GetMapping("/pets-info")
    public CommonResponseEntity<PetInfos> getPetsInfo(PayloadDto payloadDto) {
        return success(userAccountService.getPetInfos(payloadDto.getAccountId()));
    }

    @PostMapping("/modify-pet")
    public CommonResponseEntity<String> modifyPetProfile(@RequestBody ModifyPetInfo request, PayloadDto payloadDto) {
        userAccountService.modifyPetProfile(request, payloadDto.getAccountId());
        return success(PET_PROFILE_MODIFY_COMPLETED.getMessage());
    }

    @DeleteMapping("/delete-pet")
    public CommonResponseEntity<String> deletePet(@RequestBody DeletePetId request, PayloadDto payloadDto) {
        userAccountService.deletePet(request.getPetId());
        return success(DELETE_PET_COMPLETED.getMessage());
    }

    @Operation(summary = "사용자 주소, 반려동물 정보 요청", description = "사용자 주소와 반려동물 id, 사진, 이름 정보를 가져옵니다.")
    @GetMapping("/user-pets-info")
    public CommonResponseEntity<UserInfo> getUserAndPetInfos(PayloadDto payloadDto) {
        return success(userAccountService.getUserAndPetInfos(payloadDto.getAccountId()));
    }
}
