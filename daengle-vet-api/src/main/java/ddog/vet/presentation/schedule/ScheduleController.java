package ddog.vet.presentation.schedule;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.domain.vet.port.VetPersist;
import ddog.vet.application.ScheduleInfoService;
import ddog.vet.presentation.schedule.dto.ScheduleResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vet")
public class ScheduleController {
    private final ScheduleInfoService scheduleInfoService;
    private final VetPersist vetPersist;

    @GetMapping("/schedule")
    public CommonResponseEntity<ScheduleResp> getVetSchedule(PayloadDto payloadDto) {
        Long accountId = payloadDto.getAccountId();
        Long vetId = vetPersist.findByVetId(accountId).get().getVetId();

        return success(scheduleInfoService.getScheduleByVetId(vetId));
    }

}
