package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.GroomerRepository;
import ddog.daengleserver.application.repository.GroomingEstimateRepository;
import ddog.daengleserver.application.repository.UserRepository;
import ddog.daengleserver.domain.Groomer;
import ddog.daengleserver.domain.User;
import ddog.daengleserver.domain.estimate.GroomingEstimate;
import ddog.daengleserver.presentation.estimate.dto.request.GroomerGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserDesignationGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.request.UserGeneralGroomingEstimateReq;
import ddog.daengleserver.presentation.estimate.dto.response.UserGroomingEstimateDetails;
import ddog.daengleserver.presentation.estimate.dto.response.UserGroomingEstimateInfo;
import ddog.daengleserver.presentation.estimate.dto.response.UserAndPetsInfo;
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
    public void createUserGeneralGroomingEstimate(UserGeneralGroomingEstimateReq request) {
        groomingEstimateRepository.save(GroomingEstimate.createUserGeneralGroomingEstimate(request));
    }

    @Transactional
    public void createUserDesignationGroomingEstimate(UserDesignationGroomingEstimateReq request) {
        groomingEstimateRepository.save(GroomingEstimate.createUserDesignationGroomingEstimate(request));
    }

    @Transactional(readOnly = true)
    public List<UserGroomingEstimateInfo> findGroomingEstimateInfos(Long accountId) {
        String address = groomerRepository.getAddressById(accountId);
        List<GroomingEstimate> groomingEstimates = groomingEstimateRepository.findGroomingEstimatesByAddress(address);
        return GroomingEstimate.withUserGroomingEstimates(groomingEstimates);
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
