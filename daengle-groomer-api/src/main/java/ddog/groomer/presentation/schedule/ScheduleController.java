package ddog.groomer.presentation.schedule;

import ddog.auth.dto.PayloadDto;
import ddog.auth.exception.common.CommonResponseEntity;
import ddog.domain.groomer.port.GroomerPersist;
import ddog.groomer.application.ScheduleInfoService;
import ddog.groomer.presentation.schedule.dto.ScheduleResp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ddog.auth.exception.common.CommonResponseEntity.success;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/groomer")
public class ScheduleController {
    private final ScheduleInfoService scheduleInfoService;
    private final GroomerPersist groomerPersist;

    @GetMapping("/schedule")
    public CommonResponseEntity<ScheduleResp> getGroomerSchedule(PayloadDto payloadDto) {
        Long accountId = payloadDto.getAccountId();
        Long groomerId = groomerPersist.findByAccountId(accountId).get().getGroomerId();

        return success(scheduleInfoService.getScheduleByGroomerId(groomerId));
    }

}
