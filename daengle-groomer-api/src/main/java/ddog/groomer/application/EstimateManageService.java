package ddog.groomer.application;

import ddog.domain.estimate.port.GroomingEstimatePersist;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.groomer.presentation.estimate.dto.ReservationEstimateContent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EstimateManageService {

    private final GroomerPersist groomerPersist;
    private final GroomingEstimatePersist groomingEstimatePersist;
    public ReservationEstimateContent findEstimateDetailByGroomerIdAndPetId(Long groomerId, Long petId) {
        return ReservationEstimateContent.builder().build();
    }
}
