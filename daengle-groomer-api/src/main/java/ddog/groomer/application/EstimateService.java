package ddog.groomer.application;

import ddog.domain.estimate.GroomingEstimate;
import ddog.domain.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.groomer.application.mapper.GroomingEstimateMapper;
import ddog.groomer.presentation.estimate.dto.GroomingEstimateInfo;
import ddog.groomer.presentation.estimate.dto.GroomingEstimateDetail;
import ddog.domain.groomer.Groomer;
import ddog.persistence.port.GroomerPersist;
import ddog.persistence.port.GroomingEstimatePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstimateService {

    private final GroomerPersist groomerPersist;
    private final GroomingEstimatePersist groomingEstimatePersist;

    @Transactional(readOnly = true)
    public GroomingEstimateInfo findGroomingEstimateInfo(Long accountId) {
        Groomer groomer = groomerPersist.getGroomerByAccountId(accountId);
        List<GroomingEstimate> generalEstimates = groomingEstimatePersist.findGeneralGroomingEstimates(groomer.getAddress());
        List<GroomingEstimate> designationEstimates = groomingEstimatePersist.findDesignationGroomingEstimates(groomer.getGroomerId());
        return GroomingEstimateMapper.toGroomingEstimateInfo(generalEstimates, designationEstimates);
    }

    @Transactional(readOnly = true)
    public GroomingEstimateDetail getGroomingEstimateDetailInfo(Long groomingEstimateId) {
        GroomingEstimate groomingEstimate = groomingEstimatePersist.getByGroomingEstimateId(groomingEstimateId);
        return GroomingEstimateMapper.toGroomingEstimateDetail(groomingEstimate);
    }

    @Transactional
    public void createGroomerGroomingEstimate(GroomerGroomingEstimateReq request, Long accountId) {
        GroomingEstimate groomingEstimate = groomingEstimatePersist.getByGroomingEstimateId(request.getId());
        Groomer groomer = groomerPersist.getGroomerByAccountId(accountId);
        groomingEstimatePersist.save(groomingEstimate.createGroomerGroomingEstimate(request, groomer));
    }
}
