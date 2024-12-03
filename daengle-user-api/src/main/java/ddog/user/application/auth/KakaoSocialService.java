package ddog.user.application.auth;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParser;
import ddog.auth.exception.AuthException;
import ddog.auth.exception.AuthExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoSocialService {

    private static final String KAKAO_TOKEN_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private static HttpURLConnection getKakaoServerConnectionForTokenInfo(String kakaoAccessToken) throws IOException {
        URL url = new URL(KAKAO_TOKEN_INFO_URL);
        HttpURLConnection kakaoServerConnection = (HttpURLConnection) url.openConnection();
        kakaoServerConnection.setRequestMethod("GET");
        kakaoServerConnection.setRequestProperty("Authorization", "Bearer " + kakaoAccessToken);
        return kakaoServerConnection;
    }

    public String getKakaoEmail(String kakaoAccessToken) {
        try {
            HttpURLConnection kakaoServerConnection = getKakaoServerConnectionForTokenInfo(kakaoAccessToken);
            if (kakaoServerConnection.getResponseCode() == 200) {
                return getKakaoUserInfoResponse(kakaoServerConnection);
            }
            throw new AuthException(AuthExceptionType.RESPONSE_CODE_ERROR);
        } catch (IOException e) {
            throw new AuthException(AuthExceptionType.FAILED_TO_RETRIEVE_KAKAO_USER_INFO);
        }
    }

    private String getKakaoUserInfoResponse(HttpURLConnection kakaoServerConnection) throws IOException {
        String responseBody = readResponse(kakaoServerConnection);
        JsonElement jsonElement = JsonParser.parseString(responseBody);

        return jsonElement.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
    }

    private String readResponse(HttpURLConnection kakaoServerConnection) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(kakaoServerConnection.getInputStream()));
        String responseBodyInput = "";
        StringBuilder responseBody = new StringBuilder();
        while ((responseBodyInput = br.readLine()) != null) {
            responseBody.append(responseBodyInput);
        }
        br.close();
        return responseBody.toString();
    }
}
