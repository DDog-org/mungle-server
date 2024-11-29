package ddog.groomer.application;

import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.domain.estimate.dto.response.GroomingEstimateInfo;
import ddog.domain.estimate.dto.response.UserGroomingEstimateDetail;
import ddog.domain.groomer.Groomer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final GroomerRepository groomerRepository;
    private final GroomingEstimateRepository groomingEstimateRepository;

    @Transactional(readOnly = true)
    public GroomingEstimateInfo findGroomingEstimateInfo(Long accountId) {
        Groomer groomer = groomerRepository.getGroomerByAccountId(accountId);
        List<GroomingEstimate> generalEstimates = groomingEstimateRepository.findGeneralGroomingEstimates(groomer.getAddress());
        List<GroomingEstimate> designationEstimates = groomingEstimateRepository.findDesignationGroomingEstimates(groomer.getGroomerId());
        return GroomingEstimate.toGroomingEstimateInfo(generalEstimates, designationEstimates);
    }

    @Transactional(readOnly = true)
    public UserGroomingEstimateDetail getGroomingEstimateDetailInfo(Long groomingEstimateId) {
        GroomingEstimate groomingEstimate = groomingEstimateRepository.getByGroomingEstimateId(groomingEstimateId);
        return groomingEstimate.toUserGroomingEstimateDetail();
    }

    @Transactional
    public void createGroomerGroomingEstimate(GroomerGroomingEstimateReq request, Long accountId) {
        GroomingEstimate groomingEstimate = groomingEstimateRepository.getByGroomingEstimateId(request.getId());
        Groomer groomer = groomerRepository.getGroomerByAccountId(accountId);
        groomingEstimateRepository.save(groomingEstimate.createGroomerGroomingEstimate(request, groomer));
    }
}
