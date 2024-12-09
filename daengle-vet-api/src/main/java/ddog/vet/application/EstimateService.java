package ddog.vet.application;

import ddog.domain.estimate.CareEstimate;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.domain.vet.Vet;
import ddog.persistence.mysql.port.CareEstimatePersist;
import ddog.persistence.mysql.port.PetPersist;
import ddog.persistence.mysql.port.UserPersist;
import ddog.persistence.mysql.port.VetPersist;
import ddog.vet.application.exception.CareEstimateException;
import ddog.vet.application.exception.CareEstimateExceptionType;
import ddog.vet.application.exception.account.*;
import ddog.vet.application.mapper.CareEstimateMapper;
import ddog.vet.presentation.estimate.dto.CreatePendingEstimateReq;
import ddog.vet.presentation.estimate.dto.EstimateDetail;
import ddog.vet.presentation.estimate.dto.EstimateInfo;
import ddog.vet.presentation.estimate.dto.EstimateResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final VetPersist vetPersist;
    private final PetPersist petPersist;
    private final UserPersist userPersist;

    private final CareEstimatePersist careEstimatePersist;

    @Transactional(readOnly = true)
    public EstimateInfo findEstimates(Long accountId) {
        Vet vet = vetPersist.findByAccountId(accountId)
                .orElseThrow(() -> new VetException(VetExceptionType.VET_NOT_FOUND));

        List<CareEstimate> generalEstimates = careEstimatePersist.findCareEstimatesByAddress(vet.getAddress());
        List<CareEstimate> designationEstimates = careEstimatePersist.findCareEstimatesByVetId(vet.getAccountId());

        List<EstimateInfo.Content> allContents = convertEstimatesToContents(generalEstimates);
        List<EstimateInfo.Content> designationContents = convertEstimatesToContents(designationEstimates);

        allContents.addAll(designationContents);

        // estimateId 기준으로 오름차순 정렬
        allContents.sort(Comparator.comparing(EstimateInfo.Content::getId));

        return EstimateInfo.builder()
                .allEstimates(allContents)
                .designationEstimates(designationContents)
                .build();
    }

    private List<EstimateInfo.Content> convertEstimatesToContents(List<CareEstimate> estimates) {
        List<EstimateInfo.Content> contents = new ArrayList<>();

        for (CareEstimate estimate : estimates) {
            User user = userPersist.findByAccountId(estimate.getUserId())
                    .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

            Pet pet = petPersist.findByPetId(estimate.getPetId())
                    .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

            contents.add(CareEstimateMapper.mapToContent(estimate, user, pet));
        }
        return contents;
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
        careEstimatePersist.save(pendingEstimate);

        return EstimateResp.builder()
                .requestResult("대기 진료 견적서 등록 완료")
                .build();
    }
}
