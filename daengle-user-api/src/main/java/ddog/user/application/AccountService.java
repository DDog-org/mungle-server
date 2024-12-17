package ddog.user.application;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.account.port.AccountPersist;
import ddog.domain.payment.Payment;
import ddog.domain.payment.port.PaymentPersist;
import ddog.domain.pet.Breed;
import ddog.domain.pet.Pet;
import ddog.domain.pet.port.PetPersist;
import ddog.domain.user.User;
import ddog.domain.user.port.UserPersist;
import ddog.user.application.exception.account.PetException;
import ddog.user.application.exception.account.PetExceptionType;
import ddog.user.application.exception.account.UserException;
import ddog.user.application.exception.account.UserExceptionType;
import ddog.user.application.mapper.PetMapper;
import ddog.user.application.mapper.UserMapper;
import ddog.user.presentation.account.dto.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {


    private final PetPersist petPersist;
    private final UserPersist userPersist;
    private final AccountPersist accountPersist;
    private final PaymentPersist paymentPersist;

    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public ValidResponse.Nickname hasNickname(String nickname) {
        User.validateNickname(nickname);

        return ValidResponse.Nickname.builder()
                .isAvailable(!userPersist.hasNickname(nickname))
                .build();
    }

    @Transactional(readOnly = true)
    public BreedList getBreedInfos() {

        List<BreedList.Detail> details = new ArrayList<>();

        for (Breed breed : Breed.values()) {
            details.add(BreedList.Detail.builder()
                    .breed(breed.toString())
                    .breedName(breed.getName())
                    .build());
        }

        return BreedList.builder()
                .breedList(details)
                .build();
    }

    @Transactional
    public SignUpResp signUpWithPet(SignUpWithPet request, HttpServletResponse response) {
        validateSignUpWithPetDataFormat(request);

        Account accountToSave = Account.createUser(request.getEmail(), Role.DAENGLE);
        Account savedAccount = accountPersist.save(accountToSave);

        Pet pet = PetMapper.toJoinPetInfo(savedAccount.getAccountId(), request);

        User user = UserMapper.createWithPet(savedAccount.getAccountId(), request, pet);

        petPersist.save(pet);
        userPersist.save(user);

        Authentication authentication = getAuthentication(savedAccount.getAccountId(), request.getEmail());
        String accessToken = jwtTokenProvider.generateToken(authentication, response);

        return SignUpResp.builder()
                .accessToken(accessToken)
                .build();
    }

    private void validateSignUpWithPetDataFormat(SignUpWithPet request) {
        User.validateUsername(request.getUsername());
        User.validatePhoneNumber(request.getPhoneNumber());
        User.validateNickname(request.getNickname());
        User.validateAddress(request.getAddress());

        Pet.validatePetName(request.getPetName());
        Pet.validatePetBirth(request.getPetBirth());
        Pet.validatePetGender(request.getPetGender());
        Pet.validatePetWeight(request.getPetWeight());
        Pet.validateBreed(request.getBreed());
    }

    @Transactional
    public SignUpResp signUpWithoutPet(SignUpWithoutPet request, HttpServletResponse response) {
        validateSignUpWithoutPetDataFormat(request);

        Account accountToSave = Account.createUser(request.getEmail(), Role.DAENGLE);
        Account savedAccount = accountPersist.save(accountToSave);

        User user = UserMapper.createWithoutPet(savedAccount.getAccountId(), request);
        userPersist.save(user);

        Authentication authentication = getAuthentication(savedAccount.getAccountId(), request.getEmail());
        String accessToken = jwtTokenProvider.generateToken(authentication, response);

        return SignUpResp.builder()
                .accessToken(accessToken)
                .build();
    }

    private void validateSignUpWithoutPetDataFormat(SignUpWithoutPet request) {
        User.validateUsername(request.getUsername());
        User.validatePhoneNumber(request.getPhoneNumber());
        User.validateNickname(request.getNickname());
        User.validateAddress(request.getAddress());
    }

    private Authentication getAuthentication(Long accountId, String email) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_DAENGLE"));

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(email + "," + accountId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Transactional(readOnly = true)
    public ProfileInfo.UpdatePage getUserProfileInfo(Long accountId) {
        User user = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        return UserMapper.toUserProfileInfo(user);
    }

    @Transactional
    public AccountResp updateUserInfo(UpdateUserInfoReq request, Long accountId) {
        User.validateUsername(request.getNickname());

        User user = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        User modifiedUser = user.withImageAndNickname(request.getImage(), request.getNickname());

        userPersist.save(modifiedUser);

        return AccountResp.builder()
                .requestResult("사용자 프로필 수정 완료")
                .build();
    }

    @Transactional
    public AccountResp addPet(AddPetInfo request, Long accountId) {
        validateAddPetInfoDataFormat(request);

        User user = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        Pet newPet = PetMapper.create(accountId, request);
        Pet savedPet = petPersist.save(newPet);

        userPersist.save(user.withNewPet(savedPet));

        return AccountResp.builder()
                .requestResult("반려견 등록 완료")
                .build();
    }

    private void validateAddPetInfoDataFormat(AddPetInfo request) {
        Pet.validatePetName(request.getName());
        Pet.validatePetBirth(request.getBirth());
        Pet.validatePetGender(request.getGender());
        Pet.validateBreed(request.getBreed());
        Pet.validatePetWeight(request.getWeight());
        Pet.validatePetDislikeParts(request.getDislikeParts());
        Pet.validateSignificantTags(request.getSignificantTags());
    }

    @Transactional(readOnly = true)
    public PetInfo getPetInfo(Long accountId) {
        User user = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        return UserMapper.toPetInfo(user);
    }

    @Transactional
    public AccountResp updatePetInfo(UpdatePetInfo request, Long accountId) {
        this.validateModifyPetInfoDataFormat(request);

        /* TODO 수정할 반려견이 해당 사용자의 반려견인지 유효성 검증 추가해야할듯 */
        Pet modifiedPet = PetMapper.withModifyPetInfo(request, accountId);
        petPersist.save(modifiedPet);

        return AccountResp.builder()
                .requestResult("반려견 프로필 수정 완료")
                .build();
    }

    private void validateModifyPetInfoDataFormat(UpdatePetInfo request) {
        Pet.validatePetName(request.getName());
        Pet.validatePetBirth(request.getBirth());
        Pet.validatePetGender(request.getGender());
        Pet.validateBreed(request.getBreed());
        Pet.validatePetWeight(request.getWeight());
        Pet.validatePetDislikeParts(request.getDislikeParts());
        Pet.validateSignificantTags(request.getSignificantTags());
    }

    @Transactional
    public AccountResp deletePet(Long petId) {
        petPersist.findByPetId(petId)
                .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        petPersist.deletePetById(petId);

        return AccountResp.builder()
                .requestResult("반려견 프로필 삭제 완료")
                .build();
    }

    public WithdrawResp getWithdrawInfo(Long accountId) {
        User savedUser = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        Optional<List<Payment>> paymentList = paymentPersist.findByPayerId(savedUser.getAccountId());
        Integer count = paymentList.map(List::size).orElse(0);

        return WithdrawResp.builder()
                .waitingForServiceCount(count)
                .build();
    }
}
