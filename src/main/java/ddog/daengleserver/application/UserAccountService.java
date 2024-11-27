package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.AccountRepository;
import ddog.daengleserver.application.repository.PetRepository;
import ddog.daengleserver.application.repository.UserRepository;
import ddog.daengleserver.domain.Account;
import ddog.daengleserver.domain.Pet;
import ddog.daengleserver.domain.User;
import ddog.daengleserver.presentation.account.dto.request.JoinUserWithPet;
import ddog.daengleserver.presentation.account.dto.request.JoinUserWithoutPet;
import ddog.daengleserver.presentation.account.dto.request.UserProfileModifyReq;
import ddog.daengleserver.presentation.account.dto.response.UserProfileInfo;
import ddog.daengleserver.presentation.estimate.dto.response.UserAndPetsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Transactional(readOnly = true)
    public Boolean hasNickname(String nickname) {
        return !userRepository.hasNickname(nickname);
    }

    @Transactional
    public void createUserWithoutPet(JoinUserWithoutPet request) {
        Account accountToSave = Account.create(request.getEmail(), request.getRole());
        Account savedAccount = accountRepository.save(accountToSave);
        User user = User.createWithoutPet(savedAccount.getAccountId(), request);
        userRepository.save(user);
    }

    @Transactional
    public void createUserWithPet(JoinUserWithPet request) {
        Account accountToSave = Account.create(request.getEmail(), request.getRole());
        Account savedAccount = accountRepository.save(accountToSave);
        Pet pet = Pet.toJoinPetInfo(savedAccount.getAccountId(), request);
        User user = User.createWithPet(savedAccount.getAccountId(), request, pet);
        petRepository.save(pet);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserProfileInfo getUserProfileInfo(Long userId) {
        User user = userRepository.findByAccountId(userId);
        return user.toUserProfileInfo();
    }

    @Transactional
    public void modifyUserProfile(UserProfileModifyReq request, Long userId) {
        User user = userRepository.findByAccountId(userId);
        User modifiedUser = user.withImageAndNickname(request.getUserImage(), request.getNickname());
        userRepository.save(modifiedUser);
    }

    @Transactional(readOnly = true)
    public UserAndPetsInfo getUserAddressAndPetsInfoById(Long userId) {
        User user = userRepository.findByAccountId(userId);
        return user.findAddressAndPetsInfo();
    }
}
