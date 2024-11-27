package ddog.daengleserver.application;

import ddog.daengleserver.presentation.dto.request.MessageDto;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Service
public class KakaoPushService extends HttpCallService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String MSG_SEND_SERVICE_URL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
    private static final String SEND_SUCCESS_MSG = "메시지 전송에 성공했습니다.";
    private static final String SEND_FAIL_MSG = "메시지 전송에 실패했습니다.";

    private static final String SUCCESS_CODE = "0";

    public boolean sendMessage(String accessToken, MessageDto messageDto) {
        JSONObject object = new JSONObject();
        object.put("web_url", messageDto.getWebUrl());
        object.put("mobile_web_url", messageDto.getMobileUrl());

        JSONObject templateObject = new JSONObject();
        templateObject.put("object_type", messageDto.getObjType());
        templateObject.put("text", messageDto.getText());
        templateObject.put("link", object);
        templateObject.put("button_title", messageDto.getBtnTitle());

        HttpHeaders header = new HttpHeaders();
        header.set("Content-Type", APP_TYPE_URL_ENCODED);
        header.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("template_object", templateObject.toString());

        HttpEntity<?> messageRequestEntity = httpClientEntity(header, param);

        String resCode = "";
        ResponseEntity<String> response = httpRequest(MSG_SEND_SERVICE_URL, HttpMethod.POST, messageRequestEntity);

        JSONObject jsonData = new JSONObject(response.getBody());
        resCode = jsonData.get("result_code").toString();

        return successCheck(resCode);
    }

    public boolean successCheck(String resultCode) {
        if (resultCode.equals(SUCCESS_CODE)) {
            logger.info(SEND_SUCCESS_MSG);
            return true;
        }
        else {
            logger.debug(SEND_FAIL_MSG);
            return false;
        }
    }
}
