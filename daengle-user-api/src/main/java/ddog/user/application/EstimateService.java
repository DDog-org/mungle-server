package ddog.user.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.groomer.Groomer;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.domain.vet.Vet;
import ddog.persistence.mysql.port.*;
import ddog.user.application.exception.*;
import ddog.user.application.mapper.CareEstimateMapper;
import ddog.user.application.mapper.GroomingEstimateMapper;
import ddog.user.presentation.estimate.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final UserPersist userPersist;
    private final PetPersist petPersist;
    private final GroomerPersist groomerPersist;
    private final VetPersist vetPersist;

    private final GroomingEstimatePersist groomingEstimatePersist;
    private final CareEstimatePersist careEstimatePersist;

    @Transactional(readOnly = true)
    public UserInfo.Grooming getGroomerAndUserInfo(Long groomerId, Long userId) {

        User user = userPersist.findByAccountId(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        if (groomerId == null) {
            return GroomingEstimateMapper.mapToUserInfo(user);
        }

        Groomer groomer = groomerPersist.findByAccountId(groomerId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        return GroomingEstimateMapper.mapToUserInfoWithGroomer(groomer, user);
    }

    @Transactional(readOnly = true)
    public UserInfo.Care getVetAndUserInfo(Long vetId, Long userId) {

        User user = userPersist.findByAccountId(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        if (vetId == null) {
            return CareEstimateMapper.mapToUserInfo(user);
        }

        Vet vet = vetPersist.findByAccountId(vetId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        return CareEstimateMapper.mapToUserInfoWithVet(vet, user);
    }

    @Transactional
    public EstimateResp createGroomingEstimate(CreateGroomingEstimateReq request, Long accountId) {
        User.validateAddress(request.getAddress());
        GroomingEstimate.validateRequirements(request.getRequirements());

        if (request.getGroomerId() == null) {
            GroomingEstimate newEstimate = GroomingEstimateMapper.createGeneralGroomingEstimate(request, accountId);
            groomingEstimatePersist.save(newEstimate);

            return EstimateResp.builder()
                    .requestResult("(일반)신규 미용 견적서 등록 완료")
                    .build();
        }

        GroomingEstimate newEstimate = GroomingEstimateMapper.createDesignationGroomingEstimate(request, accountId);
        groomingEstimatePersist.save(newEstimate);

        return EstimateResp.builder()
                .requestResult("(지정)신규 미용 견적서 등록 완료")
                .build();
    }

    @Transactional
    public EstimateResp createCareEstimate(CreateCareEstimateReq request, Long accountId) {
        User.validateAddress(request.getAddress());
        CareEstimate.validateSymptoms(request.getSymptoms());
        CareEstimate.validateRequirements(request.getRequirements());

        if (request.getVetId() == null) {
            CareEstimate newEstimate = CareEstimateMapper.createGeneralCareEstimate(request, accountId);
            careEstimatePersist.save(newEstimate);

            return EstimateResp.builder()
                    .requestResult("(일반)신규 진료 견적서 등록 완료")
                    .build();
        }

        CareEstimate newEstimate = CareEstimateMapper.createDesignationCareEstimate(request, accountId);
        careEstimatePersist.save(newEstimate);

        return EstimateResp.builder()
                .requestResult("(지정)신규 진료 견적서 등록 완료")
                .build();
    }

    @Transactional(readOnly = true)
    public EstimateInfo findEstimates(Long accountId) {
        User user = userPersist.findByAccountId(accountId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        List<Pet> pets = user.getPets();

        List<EstimateInfo.PetInfo> petInfos = new ArrayList<>();
        for (Pet pet : pets) {
            List<GroomingEstimate> groomingEstimates = groomingEstimatePersist.findGroomingEstimatesByPetId(pet.getPetId());
            List<CareEstimate> careEstimates = careEstimatePersist.findCareEstimatesByPetId(pet.getPetId());

            List<EstimateInfo.PetInfo.Grooming> groomingInfos = new ArrayList<>();
            for (GroomingEstimate estimate : groomingEstimates) {

                Groomer groomer = groomerPersist.findByAccountId(estimate.getGroomerId())
                        .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));
                groomingInfos.add(GroomingEstimateMapper.mapToGrooming(estimate, groomer));
            }

            List<EstimateInfo.PetInfo.Care> careInfos = new ArrayList<>();
            for (CareEstimate estimate : careEstimates) {

                Vet vet = vetPersist.findByAccountId(estimate.getVetId())
                        .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));
                careInfos.add(CareEstimateMapper.mapToCare(estimate, vet));
            }

            petInfos.add(EstimateInfo.PetInfo.builder()
                    .petId(pet.getPetId())
                    .name(pet.getPetName())
                    .image(pet.getPetImage())
                    .groomingEstimates(groomingInfos)
                    .careEstimates(careInfos)
                    .build());
        }

        return EstimateInfo.builder()
                .petInfos(petInfos)
                .build();
    }

    @Transactional(readOnly = true)
    public GroomingEstimateDetail getGroomingEstimateDetail(Long groomingEstimateId) {
        GroomingEstimate groomingEstimate = groomingEstimatePersist.findByEstimateId(groomingEstimateId)
                .orElseThrow(() -> new GroomingEstimateException(GroomingEstimateExceptionType.GROOMING_ESTIMATE_NOT_FOUND));

        Groomer groomer = groomerPersist.findByAccountId(groomingEstimate.getGroomerId())
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        Pet pet = petPersist.findByPetId(groomingEstimate.getPetId())
                .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        return GroomingEstimateMapper.mapToEstimateDetail(groomingEstimate, groomer, pet);
    }

    @Transactional(readOnly = true)
    public CareEstimateDetail getCareEstimateDetail(Long careEstimateId) {
        CareEstimate careEstimate = careEstimatePersist.findByEstimateId(careEstimateId)
                .orElseThrow(() -> new CareEstimateException(CareEstimateExceptionType.CARE_ESTIMATE_NOT_FOUND));

        Vet vet = vetPersist.findByAccountId(careEstimate.getVetId())
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        Pet pet = petPersist.findByPetId(careEstimate.getPetId())
                .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        return CareEstimateMapper.mapToEstimateDetail(careEstimate, vet, pet);
    }
}
