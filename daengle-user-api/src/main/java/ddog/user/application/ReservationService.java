package ddog.user.application;

import ddog.persistence.mysql.adapter.ReservationRepository;
import ddog.persistence.mysql.port.ReservationPersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationPersist reservationPersist;




}
