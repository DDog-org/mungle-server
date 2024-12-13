package ddog.domain.groomer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class License {

    private Long licenseId;
    private Long accountId;
    private String imageUrl;
    private String name;
    private LocalDateTime acquisitionDate;

    public static License createWithImageUrl(Long accountId, String imageUrl) {
        return License.builder()
                .accountId(accountId)
                .imageUrl(imageUrl)
                .name("")
                .acquisitionDate(null)
                .build();
    }
}
