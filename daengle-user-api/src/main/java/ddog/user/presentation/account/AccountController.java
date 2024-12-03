package ddog.user.presentation.account;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.user.application.AccountService;
import ddog.user.presentation.account.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static ddog.auth.exception.common.CommonResponseEntity.success;
import static ddog.user.presentation.account.AccountControllerResp.*;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/available-nickname")
    public CommonResponseEntity<ValidResponse.Nickname> hasNickname(@RequestBody CheckNickname request) {
        return success(accountService.hasNickname(request.getNickname()));
    }

    @GetMapping("/breed-list")
    public CommonResponseEntity<BreedList> getBreedList() {
        return success(accountService.getBreedInfos());
    }

    @PostMapping("/join-with-pet")
    public CommonResponseEntity<SignUpResp> joinUserWithPet(@RequestBody SignUpWithPet request, HttpServletResponse response) {
        return success(accountService.signUpWithPet(request, response));
    }

    @PostMapping("/join-without-pet")
    public CommonResponseEntity<SignUpResp> joinUserWithoutPet(@RequestBody SignUpWithoutPet request, HttpServletResponse response) {
        return success(accountService.signUpWithoutPet(request, response));
    }

    @GetMapping("/modify-page")
    public CommonResponseEntity<ProfileInfo.ModifyPage> getUserProfileInfo(PayloadDto payloadDto) {
        return success(accountService.getUserProfileInfo(payloadDto.getAccountId()));
    }

    @PatchMapping("/info")
    public CommonResponseEntity<String> modifyUserInfo(@RequestBody UserInfoModifyReq request, PayloadDto payloadDto) {
        accountService.modifyUserInfo(request, payloadDto.getAccountId());
        return success(PROFILE_MODIFY_COMPLETED.getMessage());
    }

    @PostMapping("/pet")
    public CommonResponseEntity<String> addPet(@RequestBody AddPetInfo request, PayloadDto payloadDto) {
        accountService.addPet(request, payloadDto.getAccountId());
        return success(PET_ADD_COMPLETED.getMessage());
    }

    @GetMapping("/pet-info")
    public CommonResponseEntity<PetInfo> getPetInfo(PayloadDto payloadDto) {
        return success(accountService.getPetInfo(payloadDto.getAccountId()));
    }

    @PatchMapping("/pet-info")
    public CommonResponseEntity<String> modifyPetInfo(@RequestBody ModifyPetInfo request, PayloadDto payloadDto) {
        accountService.modifyPetInfo(request, payloadDto.getAccountId());
        return success(PET_PROFILE_MODIFY_COMPLETED.getMessage());
    }

    @DeleteMapping("/pet")
    public CommonResponseEntity<String> deletePet(@RequestBody DeletePetId request) {
        accountService.deletePet(request.getPetId());
        return success(DELETE_PET_COMPLETED.getMessage());
    }
}
