package ddog.daengleserver.global.auth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ddog.daengleserver.global.auth.dto.KakaoDto;
import ddog.daengleserver.global.auth.exception.AuthException;
import ddog.daengleserver.global.auth.exception.enums.AuthExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
@Component
public class KakaoUtil {

    public static final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    public static final String INFO_URL = "https://kapi.kakao.com/v2/user/me";
    public static final String PAGE_URL = "https://kauth.kakao.com/oauth/authorize";

    @Value("${kakao.auth.client}")
    private String client;

    @Value("${kakao.auth.redirect}")
    private String redirect;

    public KakaoDto.OAuthToken requestToken(String kakaoAccessToken) {
        RestTemplate restTemplate = new RestTemplate();
        new HttpHeaders().add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", client);
        params.add("redirect_uri", redirect);
        params.add("code", kakaoAccessToken);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, new HttpHeaders());

        ResponseEntity<String> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, kakaoTokenRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoDto.OAuthToken oAuthToken = null;

        try {
            oAuthToken = objectMapper.readValue(response.getBody(), KakaoDto.OAuthToken.class);
            log.info("oAuthToken : {}", oAuthToken.getAccess_token());
        } catch (JsonProcessingException e) {
            throw new AuthException(AuthExceptionType.RESPONSE_CODE_ERROR);
        }
        return oAuthToken;
    }

    public KakaoDto.KakaoProfile requestProfile(KakaoDto.OAuthToken oAuthToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
        headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(INFO_URL, HttpMethod.GET, kakaoProfileRequest, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        KakaoDto.KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper.readValue(response.getBody(), KakaoDto.KakaoProfile.class);
        } catch (JsonProcessingException e) {
            log.info(Arrays.toString(e.getStackTrace()));
            throw new AuthException(AuthExceptionType.RESPONSE_CODE_ERROR);
        }
        return kakaoProfile;
    }

    public String createURL() {
        return PAGE_URL
                + "?response_type=code"
                + "&client_id="
                + client
                + "&redirect_uri="
                + redirect;
    }
}
