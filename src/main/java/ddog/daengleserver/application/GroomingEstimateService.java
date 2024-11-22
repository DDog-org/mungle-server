package ddog.daengleserver.application;

import ddog.daengleserver.application.repository.UserRepository;
import ddog.daengleserver.domain.User;
import ddog.daengleserver.presentation.dto.response.UserAddressAndPetsInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroomingEstimateService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserAddressAndPetsInfo getUserAddressAndPetsInfoById(Long userId) {
        User user = userRepository.findById(userId);
        return user.getAddressAndPetsInfo();
    }
}
