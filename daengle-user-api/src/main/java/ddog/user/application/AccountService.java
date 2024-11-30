package ddog.user.application;

import ddog.domain.account.Account;
import ddog.domain.estimate.dto.response.UserInfo;
import ddog.domain.pet.Breed;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.domain.user.dto.request.*;
import ddog.domain.user.dto.response.BreedInfo;
import ddog.domain.user.dto.response.PetInfo;
import ddog.domain.user.dto.response.UserProfileInfo;
import ddog.persistence.port.AccountPersist;
import ddog.persistence.port.PetPersist;
import ddog.persistence.port.UserPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountPersist accountPersist;
    private final UserPersist userPersist;
    private final PetPersist petPersist;

    @Transactional(readOnly = true)
    public Boolean hasNickname(String nickname) {
        return !userPersist.hasNickname(nickname);
    }

    /* TODO PetService 추가하면 그곳으로 옮기기 */
    public BreedInfo getBreedInfos() {
        List<BreedInfo.Detail> details = new ArrayList<>();
        for (Breed breed : Breed.values()) {
            details.add(BreedInfo.Detail.builder()
                    .breed(breed.toString())
                    .breedName(breed.getName())
                    .build());
        }
        return BreedInfo.builder()
                .breedList(details)
                .build();
    }

    @Transactional
    public void createUserWithPet(JoinUserWithPet request) {
        Account accountToSave = Account.create(request.getEmail(), request.getRole());
        Account savedAccount = accountPersist.save(accountToSave);
        Pet pet = Pet.toJoinPetInfo(savedAccount.getAccountId(), request);
        User user = User.createWithPet(savedAccount.getAccountId(), request, pet);
        petPersist.save(pet);
        userPersist.save(user);
    }

    @Transactional
    public void createUserWithoutPet(JoinUserWithoutPet request) {
        Account accountToSave = Account.create(request.getEmail(), request.getRole());
        Account savedAccount = accountPersist.save(accountToSave);
        User user = User.createWithoutPet(savedAccount.getAccountId(), request);
        userPersist.save(user);
    }

    @Transactional(readOnly = true)
    public UserProfileInfo getUserProfileInfo(Long accountId) {
        User user = userPersist.findByAccountId(accountId);
        return user.toUserProfileInfo();
    }

    @Transactional
    public void modifyUserProfile(UserProfileModifyReq request, Long accountId) {
        User user = userPersist.findByAccountId(accountId);
        User modifiedUser = user.withImageAndNickname(request.getImage(), request.getNickname());
        userPersist.save(modifiedUser);
    }

    @Transactional
    public void addPet(AddPetInfo request, Long accountId) {
        User user = userPersist.findByAccountId(accountId);
        Pet newPet = Pet.create(accountId, request);
        Pet savedPet = petPersist.save(newPet);
        userPersist.save(user.withNewPet(savedPet));
    }

    @Transactional(readOnly = true)
    public PetInfo getPetInfo(Long accountId) {
        User user = userPersist.findByAccountId(accountId);
        return user.toPetInfo();
    }

    @Transactional
    public void modifyPetProfile(ModifyPetInfo request, Long accountId) {
        /* TODO 수정할 반려견이 해당 사용자의 반려견인지 유효성 검증 추가해야할듯 */
        Pet modifiedPet = Pet.withModifyPetInfo(request, accountId);
        petPersist.save(modifiedPet);
    }

    @Transactional(readOnly = true)
    public UserInfo getUserAndPetInfos(Long userId) {
        User user = userPersist.findByAccountId(userId);
        return user.toUserInfo();
    }

    @Transactional
    public void deletePet(Long petId) {
        /* TODO PetService 추가하면 그곳으로 옮기기 */
        petPersist.deletePetById(petId);
    }
}
