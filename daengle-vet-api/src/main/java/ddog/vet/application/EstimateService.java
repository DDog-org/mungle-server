package ddog.vet.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.estimate.EstimateStatus;
import ddog.domain.estimate.Proposal;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.domain.vet.Vet;
import ddog.domain.estimate.port.CareEstimatePersist;
import ddog.domain.pet.port.PetPersist;
import ddog.domain.user.port.UserPersist;
import ddog.domain.vet.port.VetPersist;
import ddog.vet.application.exception.CareEstimateException;
import ddog.vet.application.exception.CareEstimateExceptionType;
import ddog.vet.application.exception.account.*;
import ddog.vet.application.mapper.CareEstimateMapper;
import ddog.vet.presentation.estimate.dto.CreatePendingEstimateReq;
import ddog.vet.presentation.estimate.dto.EstimateDetail;
import ddog.vet.presentation.estimate.dto.EstimateInfo;
import ddog.vet.presentation.estimate.dto.EstimateResp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final VetPersist vetPersist;
    private final PetPersist petPersist;
    private final UserPersist userPersist;

    private final CareEstimatePersist careEstimatePersist;

    @Transactional(readOnly = true)
    public EstimateInfo.General findGeneralEstimates(Long accountId, int page, int size) {
        Vet vet = vetPersist.findByAccountId(accountId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);

        Page<CareEstimate> estimates = careEstimatePersist.findByStatusAndProposalAndAddress(EstimateStatus.NEW, Proposal.GENERAL, vet.getAddress(), pageable);

        List<EstimateInfo.General.Content> contents = new ArrayList<>();
        for (CareEstimate estimate : estimates) {
            User user = userPersist.findByAccountId(estimate.getUserId())
                    .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

            Pet pet = petPersist.findByPetId(estimate.getPetId())
                    .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

            contents.add(CareEstimateMapper.mapToGeneralContent(estimate, user, pet));
        }
        return EstimateInfo.General.builder()
                .estimates(contents)
                .build();
    }

    @Transactional(readOnly = true)
    public EstimateInfo.Designation findDesignationEstimates(Long accountId, int page, int size) {
        Vet vet = vetPersist.findByAccountId(accountId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size);

        Page<CareEstimate> estimates = careEstimatePersist.findByStatusAndProposalAndVetId(EstimateStatus.NEW, Proposal.DESIGNATION, vet.getAccountId(), pageable);

        List<EstimateInfo.Designation.Content> contents = new ArrayList<>();
        for (CareEstimate estimate : estimates) {
            User user = userPersist.findByAccountId(estimate.getUserId())
                    .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

            Pet pet = petPersist.findByPetId(estimate.getPetId())
                    .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

            contents.add(CareEstimateMapper.mapToDesignationContent(estimate, user, pet));
        }
        return EstimateInfo.Designation.builder()
                .estimates(contents)
                .build();
    }

    @Transactional(readOnly = true)
    public EstimateDetail getEstimateDetail(Long estimateId) {
        CareEstimate careEstimate = careEstimatePersist.findByEstimateId(estimateId)
                .orElseThrow(() -> new CareEstimateException(CareEstimateExceptionType.CARE_ESTIMATE_NOT_FOUND));

        User user = userPersist.findByAccountId(careEstimate.getUserId())
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        Pet pet = petPersist.findByPetId(careEstimate.getPetId())
                .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        return CareEstimateMapper.mapToEstimateDetail(careEstimate, user, pet);
    }

    @Transactional
    public EstimateResp createPendingEstimate(CreatePendingEstimateReq request, Long accountId) {
        CareEstimate.validateDiagnosis(request.getDiagnosis());
        CareEstimate.validateCause(request.getCause());
        CareEstimate.validateTreatment(request.getTreatment());

        CareEstimate careEstimate = careEstimatePersist.findByEstimateId(request.getId())
                .orElseThrow(() -> new CareEstimateException(CareEstimateExceptionType.CARE_ESTIMATE_NOT_FOUND));

        Vet vet = vetPersist.findByAccountId(accountId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        CareEstimate pendingEstimate = CareEstimateMapper.createPendingEstimate(request, vet, careEstimate);
        CareEstimate savedEstimate = careEstimatePersist.save(pendingEstimate);

        return EstimateResp.builder()
                .estimateId(savedEstimate.getEstimateId())
                .requestResult("대기 진료 견적서 등록 완료")
                .build();
    }

    public EstimateInfo.EstimateUserInfo findByUserInfoByEstimateId(Long estimateId) {
        CareEstimate savedCareEstimate = careEstimatePersist.findByEstimateId(estimateId).orElseThrow(() -> new CareEstimateException(CareEstimateExceptionType.CARE_ESTIMATE_NOT_FOUND));
        Long userId = savedCareEstimate.getUserId();
        String userName = userPersist.findByAccountId(userId).get().getNickname();
        String userPhone = userPersist.findByAccountId(userId).get().getPhoneNumber();

        return EstimateInfo.EstimateUserInfo.builder()
                .userId(userId)
                .userNickname(userName)
                .userPhone(userPhone)
                .build();
    }
}
