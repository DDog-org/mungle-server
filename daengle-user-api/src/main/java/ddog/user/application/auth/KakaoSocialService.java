package ddog.user.application.auth;

import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonParser;
import ddog.user.application.exception.common.AuthException;
import ddog.user.application.exception.common.AuthExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoSocialService {

    private static final String KAKAO_TOKEN_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    public HashMap<String, Object> getKakaoUserInfo(String kakaoAccessToken){
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

    private static HttpURLConnection getKakaoServerConnectionForTokenInfo(String kakaoAccessToken) throws IOException {
        URL url = new URL(KAKAO_TOKEN_INFO_URL);
        HttpURLConnection kakaoServerConnection = (HttpURLConnection) url.openConnection();
        kakaoServerConnection.setRequestMethod("GET");
        kakaoServerConnection.setRequestProperty("Authorization", "Bearer " + kakaoAccessToken);
        return kakaoServerConnection;
    }

    private HashMap<String, Object> getKakaoUserInfoResponse(HttpURLConnection kakaoServerConnection) throws IOException{
        String responseBody = readResponse(kakaoServerConnection);
        JsonElement jsonElement = JsonParser.parseString(responseBody);

        String email = jsonElement.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();

        HashMap<String, Object> kakaoUserInfo = new HashMap<>();
        kakaoUserInfo.put("email", email);
        return kakaoUserInfo;
    }

    private String readResponse(HttpURLConnection kakaoServerConnection) throws IOException{
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
