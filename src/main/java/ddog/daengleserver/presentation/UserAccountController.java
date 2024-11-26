package ddog.daengleserver.presentation;

import ddog.daengleserver.application.UserService;
import ddog.daengleserver.global.common.CommonResponseEntity;
import ddog.daengleserver.presentation.dto.request.CheckNicknameReq;
import ddog.daengleserver.presentation.dto.request.JoinUserWithPet;
import ddog.daengleserver.presentation.dto.request.JoinUserWithoutPet;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ddog.daengleserver.global.common.CommonResponseEntity.success;
import static ddog.daengleserver.presentation.enums.JoinControllerResp.USER_JOIN_COMPLETED;

@RestController
@RequestMapping("/api/daengle")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserService userService;

    @PostMapping("/available-nickname")
    public CommonResponseEntity<Boolean> hasNickname(@RequestBody CheckNicknameReq request) {
        return success(userService.hasNickname(request.getNickname()));
    }

    @PostMapping("/join-without-pet")
    public CommonResponseEntity<String> joinUserWithoutPet(@RequestBody JoinUserWithoutPet request) {
        userService.createUserWithoutPet(request);
        return success(USER_JOIN_COMPLETED.getMessage());
    }

    @PostMapping("/join-with-pet")
    public CommonResponseEntity<String> joinUserWithPet(@RequestBody JoinUserWithPet request) {
        userService.createUserWithPet(request);
        return success(USER_JOIN_COMPLETED.getMessage());
    }

}
