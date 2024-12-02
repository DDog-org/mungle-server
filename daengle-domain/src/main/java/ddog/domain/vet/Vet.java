package ddog.domain.vet;

import ddog.domain.account.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vet {

    private Long vetId;
    private Long accountId;
    private String email;
    private int daengleMeter;
    private String vetName;
    private String vetImage;
    private String address;
    private String detailAddress;
    private String phoneNumber;
    private String vetIntroduction;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<Day> closedDays;
    private List<String> licenses;
}
