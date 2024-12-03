package ddog.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccessTokenInfo {
    private String grantType;
    private String accessToken;
}
