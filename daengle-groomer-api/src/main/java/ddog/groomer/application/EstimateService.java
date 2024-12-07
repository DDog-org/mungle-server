package ddog.groomer.application;

import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.groomer.Groomer;
import ddog.domain.pet.Pet;
import ddog.domain.user.User;
import ddog.groomer.application.mapper.GroomingEstimateMapper;
import ddog.groomer.presentation.estimate.dto.EstimateDetail;
import ddog.groomer.presentation.estimate.dto.EstimateInfo;
import ddog.groomer.presentation.estimate.dto.EstimateReq;
import ddog.groomer.presentation.estimate.dto.EstimateResp;
import ddog.persistence.mysql.port.*;
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
    private final GroomingEstimateLogPersist groomingEstimateLogPersist;

    @Transactional(readOnly = true)
    public EstimateInfo findEstimateInfo(Long accountId) {
        Groomer groomer = groomerPersist.findByAccountId(accountId);

        List<GroomingEstimate> generalEstimates = groomingEstimatePersist.findGeneralGroomingEstimates(groomer.getAddress());
        List<GroomingEstimate> designationEstimates = groomingEstimatePersist.findDesignationGroomingEstimates(groomer.getAccountId());

        List<EstimateInfo.Content> allContents = estimatesToContents(generalEstimates);
        List<EstimateInfo.Content> designationContents = estimatesToContents(designationEstimates);

        allContents.addAll(designationContents);

        // groomingEstimateId 기준으로 오름차순 정렬
        allContents.sort(Comparator.comparing(EstimateInfo.Content::getId));

        return EstimateInfo.builder()
                .allEstimates(allContents)
                .designationEstimates(designationContents)
                .build();
    }

    private List<EstimateInfo.Content> estimatesToContents(List<GroomingEstimate> estimates) {
        List<EstimateInfo.Content> contents = new ArrayList<>();

        for (GroomingEstimate estimate : estimates) {
            User user = userPersist.findByAccountId(estimate.getUserId());
            Pet pet = petPersist.findByPetId(estimate.getPetId());

            contents.add(GroomingEstimateMapper.estimateToContent(estimate, user, pet));
        }

        return contents;
    }

    @Transactional(readOnly = true)
    public EstimateDetail getEstimateDetail(Long groomingEstimateId) {
        GroomingEstimate groomingEstimate = groomingEstimatePersist.findByEstimateId(groomingEstimateId);

        User user = userPersist.findByAccountId(groomingEstimate.getUserId());
        Pet pet = petPersist.findByPetId(groomingEstimate.getPetId());

        return GroomingEstimateMapper.toEstimateDetail(groomingEstimate, user, pet);
    }

    @Transactional
    public EstimateResp createEstimate(EstimateReq request, Long accountId) {
        GroomingEstimate.validateOverallOpinion(request.getOverallOpinion());

        GroomingEstimate groomingEstimate = groomingEstimatePersist.findByEstimateId(request.getId());
        Groomer groomer = groomerPersist.findByAccountId(accountId);

        GroomingEstimate updatedEstimate = GroomingEstimateMapper.updateEstimate(request, groomer, groomingEstimate);
        GroomingEstimate savedEstimate = groomingEstimatePersist.save(updatedEstimate);

        GroomingEstimateLog newEstimateLog = GroomingEstimateLog.from(savedEstimate);
        groomingEstimateLogPersist.save(newEstimateLog);

        return EstimateResp.builder()
                .requestResult("대기 미용 견적서 등록 완료")
                .build();
    }
}
