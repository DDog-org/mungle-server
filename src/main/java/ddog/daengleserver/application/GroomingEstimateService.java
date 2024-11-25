package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.GroomerRepository;
import ddog.daengleserver.application.repository.GroomingEstimateRepository;
import ddog.daengleserver.domain.Groomer;
import ddog.daengleserver.domain.estimate.GroomingEstimate;
import ddog.daengleserver.presentation.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.UserGroomingEstimateDetails;
import ddog.daengleserver.presentation.estimate.dto.response.GroomingEstimateInfos;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroomingEstimateService {

    private final GroomerRepository groomerRepository;
    private final GroomingEstimateRepository groomingEstimateRepository;

    @Transactional
    public void createUserGeneralGroomingEstimate(UserGeneralGroomingEstimateReq request, Long userId) {
        groomingEstimateRepository.save(GroomingEstimate.createUserGeneralCareEstimate(request, userId));
    }

    @Transactional
    public void createUserDesignationGroomingEstimate(UserDesignationGroomingEstimateReq request, Long userId) {
        groomingEstimateRepository.save(GroomingEstimate.createUserDesignationGroomingEstimate(request, userId));
    }

    @Transactional(readOnly = true)
    public GroomingEstimateInfos findGroomingEstimateInfos(Long accountId) {
        Groomer groomer = groomerRepository.getGroomerById(accountId);
        List<GroomingEstimate> generalEstimates = groomingEstimateRepository.findGeneralGroomingEstimates(groomer.getAddress());
        List<GroomingEstimate> designationEstimates = groomingEstimateRepository.findDesignationGroomingEstimates(groomer.getGroomerId());
        return GroomingEstimate.findGroomingEstimateInfos(generalEstimates, designationEstimates);
    }

    @Transactional(readOnly = true)
    public UserGroomingEstimateDetails getGroomingEstimateDetailInfo(Long groomingEstimateId) {
        GroomingEstimate groomingEstimate = groomingEstimateRepository.getByGroomingEstimateId(groomingEstimateId);
        return groomingEstimate.withUserGroomingEstimate();
    }

    @Transactional
    public void createGroomerGroomingEstimate(GroomerGroomingEstimateReq request, Long accountId) {
        GroomingEstimate groomingEstimate = groomingEstimateRepository.getByGroomingEstimateId(request.getGroomingEstimateId());
        Groomer groomer = groomerRepository.getGroomerById(accountId);
        groomingEstimateRepository.save(groomingEstimate.createGroomerGroomingEstimate(request, groomer));
    }
}
