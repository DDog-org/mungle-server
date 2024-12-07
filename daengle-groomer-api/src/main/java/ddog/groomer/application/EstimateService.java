package ddog.groomer.application;

import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.groomer.Groomer;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.groomer.application.exception.GroomingEstimateException;
import ddog.groomer.application.exception.GroomingEstimateExceptionType;
import ddog.groomer.application.exception.account.*;
import ddog.groomer.application.mapper.GroomingEstimateMapper;
import ddog.groomer.presentation.estimate.dto.CreatePendingEstimateReq;
import ddog.groomer.presentation.estimate.dto.EstimateDetail;
import ddog.groomer.presentation.estimate.dto.EstimateInfo;
import ddog.groomer.presentation.estimate.dto.EstimateResp;
import ddog.persistence.mysql.port.GroomerPersist;
import ddog.persistence.mysql.port.GroomingEstimatePersist;
import ddog.persistence.mysql.port.PetPersist;
import ddog.persistence.mysql.port.UserPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final GroomerPersist groomerPersist;
    private final PetPersist petPersist;
    private final UserPersist userPersist;

    private final GroomingEstimatePersist groomingEstimatePersist;

    @Transactional(readOnly = true)
    public EstimateInfo findEstimates(Long accountId) {
        Groomer groomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        List<GroomingEstimate> generalEstimates = groomingEstimatePersist.findGroomingEstimatesByAddress(groomer.getAddress());
        List<GroomingEstimate> designationEstimates = groomingEstimatePersist.findGroomingEstimatesByGroomerId(groomer.getAccountId());

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

    private List<EstimateInfo.Content> convertEstimatesToContents(List<GroomingEstimate> estimates) {
        List<EstimateInfo.Content> contents = new ArrayList<>();

        for (GroomingEstimate estimate : estimates) {
            User user = userPersist.findByAccountId(estimate.getUserId())
                    .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

            Pet pet = petPersist.findByPetId(estimate.getPetId())
                    .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

            contents.add(GroomingEstimateMapper.mapToContent(estimate, user, pet));
        }
        return contents;
    }

    @Transactional(readOnly = true)
    public EstimateDetail getEstimateDetail(Long estimateId) {
        GroomingEstimate groomingEstimate = groomingEstimatePersist.findByEstimateId(estimateId)
                .orElseThrow(() -> new GroomingEstimateException(GroomingEstimateExceptionType.GROOMING_ESTIMATE_NOT_FOUND));

        User user = userPersist.findByAccountId(groomingEstimate.getUserId())
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));

        Pet pet = petPersist.findByPetId(groomingEstimate.getPetId())
                .orElseThrow(() -> new PetException(PetExceptionType.PET_NOT_FOUND));

        return GroomingEstimateMapper.mapToEstimateDetail(groomingEstimate, user, pet);
    }

    @Transactional
    public EstimateResp createPendingEstimate(CreatePendingEstimateReq request, Long accountId) {
        GroomingEstimate.validateOverallOpinion(request.getOverallOpinion());

        GroomingEstimate groomingEstimate = groomingEstimatePersist.findByEstimateId(request.getId())
                .orElseThrow(() -> new GroomingEstimateException(GroomingEstimateExceptionType.GROOMING_ESTIMATE_NOT_FOUND));

        Groomer groomer = groomerPersist.findByAccountId(accountId)
                .orElseThrow(() -> new GroomerException(GroomerExceptionType.GROOMER_NOT_FOUND));

        GroomingEstimate pendingEstimate = GroomingEstimateMapper.createPendingEstimate(request, groomer, groomingEstimate);
        groomingEstimatePersist.save(pendingEstimate);

        return EstimateResp.builder()
                .requestResult("대기 미용 견적서 등록 완료")
                .build();
    }
}
