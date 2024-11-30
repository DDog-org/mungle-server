package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.GroomerRepository;
import ddog.daengleserver.application.repository.GroomingEstimateRepository;
import ddog.daengleserver.domain.account.Groomer;
import ddog.daengleserver.domain.estimate.GroomingEstimate;
import ddog.daengleserver.presentation.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.UserGroomingEstimateDetail;
import ddog.daengleserver.presentation.estimate.dto.response.GroomingEstimateInfo;
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
    public void createUserGeneralGroomingEstimate(UserGeneralGroomingEstimateReq request, Long accountId) {
        groomingEstimateRepository.save(GroomingEstimate.createUserGeneralGroomingEstimate(request, accountId));
    }

    @Transactional
    public void createUserDesignationGroomingEstimate(UserDesignationGroomingEstimateReq request, Long accountId) {
        groomingEstimateRepository.save(GroomingEstimate.createUserDesignationGroomingEstimate(request, accountId));
    }

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
