package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.GroomerRepository;
import ddog.daengleserver.application.repository.GroomingEstimateRepository;
import ddog.daengleserver.application.repository.UserRepository;
import ddog.daengleserver.domain.User;
import ddog.daengleserver.domain.estimate.GroomingEstimate;
import ddog.daengleserver.presentation.dto.request.DesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.dto.request.GeneralGroomingEstimateReq;
import ddog.daengleserver.presentation.dto.response.GroomingEstimateDetails;
import ddog.daengleserver.presentation.dto.response.GroomingEstimateInfo;
import ddog.daengleserver.presentation.dto.response.UserAndPetsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroomingEstimateService {

    private final UserRepository userRepository;
    private final GroomerRepository groomerRepository;
    private final GroomingEstimateRepository groomingEstimateRepository;

    @Transactional(readOnly = true)
    public UserAndPetsInfo getUserAddressAndPetsInfoById(Long accountId) {
        User user = userRepository.findById(accountId);
        return user.getAddressAndPetsInfo();
    }

    @Transactional
    public void createGeneralGroomingEstimate(GeneralGroomingEstimateReq generalGroomingEstimateReq) {
        groomingEstimateRepository.save(GroomingEstimate.createGeneralGroomingEstimate(generalGroomingEstimateReq));
    }

    @Transactional
    public void createDesignationGroomingEstimate(DesignationGroomingEstimateReq designationGroomingEstimateReq) {
        groomingEstimateRepository.save(GroomingEstimate.createDesignationGroomingEstimate(designationGroomingEstimateReq));
    }

    @Transactional(readOnly = true)
    public List<GroomingEstimateInfo> findGroomingEstimateInfos(Long accountId) {
        String address = groomerRepository.getAddressById(accountId);
        List<GroomingEstimate> groomingEstimates = groomingEstimateRepository.findGroomingEstimatesByAddress(address);
        return GroomingEstimate.withGroomingEstimates(groomingEstimates);
    }

    @Transactional(readOnly = true)
    public GroomingEstimateDetails getGroomingEstimateDetailInfo(Long groomingEstimateId) {
        GroomingEstimate groomingEstimate = groomingEstimateRepository.getByGroomingEstimateId(groomingEstimateId);
        return groomingEstimate.withGroomingEstimate();
    }
}
