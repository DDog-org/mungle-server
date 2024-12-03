package ddog.user.application;

import ddog.auth.config.jwt.JwtTokenProvider;
import ddog.domain.account.Account;
import ddog.domain.account.Role;
import ddog.domain.pet.Breed;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.persistence.port.AccountPersist;
import ddog.persistence.port.PetPersist;
import ddog.persistence.port.UserPersist;
import ddog.user.application.exception.AccountException;
import ddog.user.application.exception.AccountExceptionType;
import ddog.user.application.exception.UserException;
import ddog.user.application.exception.UserExceptionType;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountPersist accountPersist;
    private final UserPersist userPersist;
    private final PetPersist petPersist;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public ValidResponse.Nickname hasNickname(String nickname) {
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
        if(this.hasInValidSignUpWithPetDataFormat(request))
            throw new UserException(UserExceptionType.INVALID_REQUEST_DATA_FORMAT);

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

    @Transactional
    public SignUpResp signUpWithoutPet(SignUpWithoutPet request, HttpServletResponse response) {
        if(this.hasInValidSignUpWithoutPetDataFormat(request))
            throw new AccountException(AccountExceptionType.INVALID_REQUEST_DATA_FORMAT);

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

    private Authentication getAuthentication(Long accountId, String email) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_DAENGLE"));

        Authentication authentication
                = new UsernamePasswordAuthenticationToken(email + "," + accountId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    @Transactional(readOnly = true)
    public ProfileInfo.ModifyPage getUserProfileInfo(Long accountId) {
        User user = userPersist.findByAccountId(accountId);
        return UserMapper.toUserProfileInfo(user);
    }

    @Transactional
    public void modifyUserInfo(UserInfoModifyReq request, Long accountId) {
        if(this.hasInValidUserInfoModifyReqDataFormat(request))
            throw new UserException(UserExceptionType.INVALID_REQUEST_DATA_FORMAT);

        User user = userPersist.findByAccountId(accountId);
        User modifiedUser = user.withImageAndNickname(request.getImage(), request.getNickname());
        userPersist.save(modifiedUser);
    }

    @Transactional
    public void addPet(AddPetInfo request, Long accountId) {
        User user = userPersist.findByAccountId(accountId);
        Pet newPet = PetMapper.create(accountId, request);
        Pet savedPet = petPersist.save(newPet);
        userPersist.save(user.withNewPet(savedPet));
    }

    @Transactional(readOnly = true)
    public PetInfo getPetInfo(Long accountId) {
        User user = userPersist.findByAccountId(accountId);
        return UserMapper.toPetInfo(user);
    }

    @Transactional
    public void modifyPetProfile(ModifyPetInfo request, Long accountId) {
        /* TODO 수정할 반려견이 해당 사용자의 반려견인지 유효성 검증 추가해야할듯 */
        Pet modifiedPet = PetMapper.withModifyPetInfo(request, accountId);
        petPersist.save(modifiedPet);
    }

    @Transactional
    public void deletePet(Long petId) {
        /* TODO PetService 추가하면 그곳으로 옮기기 */
        petPersist.deletePetById(petId);
    }

    private boolean hasInValidSignUpWithPetDataFormat(SignUpWithPet request) {
        try {
            User.validateUsername(request.getUsername());
            User.validatePhoneNumber(request.getPhoneNumber());
            User.validateNickname(request.getNickname());
            User.validateAddress(request.getAddress());

            Pet.validatePetName(request.getPetName());
            Pet.validatePetBirth(request.getPetBirth());
            Pet.validatePetGender(request.getPetGender());
            Pet.validatePetWeight(request.getPetWeight());
            Pet.validateBreed(request.getBreed());

            return false; // 모든 유효성 검사 통과
        } catch (IllegalArgumentException e) {
            return true; // 유효성 검사 실패
        }
    }

    private boolean hasInValidSignUpWithoutPetDataFormat(SignUpWithoutPet request) {
        try {
            User.validateUsername(request.getUsername());
            User.validatePhoneNumber(request.getPhoneNumber());
            User.validateNickname(request.getNickname());
            User.validateAddress(request.getAddress());

            return false; // 모든 유효성 검사 통과
        } catch (IllegalArgumentException e) {
            return true; // 유효성 검사 실패
        }
    }

    private boolean hasInValidUserInfoModifyReqDataFormat(UserInfoModifyReq request) {
        try {
            User.validateUsername(request.getNickname());

            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }
}
