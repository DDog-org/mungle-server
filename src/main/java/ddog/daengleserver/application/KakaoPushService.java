package ddog.daengleserver.application;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoPushService {

    @Value("${kakao.admin}")
    private String ADMIN_KEY; // 카카오에서 발급받은 Admin Key
    private static final String SEND_MESSAGE_URL = "https://kapi.kakao.com/v1/api/talk/friends/message/send";

    private static final Logger logger = LoggerFactory.getLogger(KakaoPushService.class);

    // 알림톡 메시지 전송
    public boolean sendNotification(String targetUserId, String message) {
        try {
            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();

            // 현재 로그인 중인 사용자의 JWT 토큰 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getCredentials() != null) {
                String jwtToken = (String) authentication.getCredentials();
                logger.info("JWT 토큰: {}", jwtToken); // 디버깅을 위해 JWT 토큰을 출력
                headers.set("Authorization", "Bearer " + jwtToken);  // JWT 토큰을 Authorization 헤더에 포함
            } else {
                throw new IllegalStateException("로그인된 사용자가 없습니다.");
            }

            headers.set("KakaoAK", ADMIN_KEY);  // 카카오 Admin Key를 헤더에 추가
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 메시지 템플릿 객체 생성
            JSONObject linkObject = new JSONObject();
            linkObject.put("web_url", message); // 여기서 웹 URL은 예시로 메시지를 그대로 넣음
            linkObject.put("mobile_web_url", message); // 모바일 URL도 동일하게 설정

            JSONObject templateObject = new JSONObject();
            templateObject.put("object_type", "text"); // 텍스트 메시지로 설정
            templateObject.put("text", message);
            templateObject.put("link", linkObject);
            templateObject.put("button_title", "자세히 보기");

            // 메시지 객체 구성
            JSONObject messageObject = new JSONObject();
            messageObject.put("receiver_uuids", new String[]{targetUserId});
            messageObject.put("template_object", templateObject);

            // 요청 전송
            HttpEntity<String> entity = new HttpEntity<>(messageObject.toString(), headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(SEND_MESSAGE_URL, HttpMethod.POST, entity, String.class);

            // 응답 확인
            if (response.getStatusCode() == HttpStatus.OK) {
                logger.info("알림톡 전송 성공: {}", response.getBody());
                return true;
            } else {
                logger.warn("알림톡 전송 실패: {}", response.getBody());
                return false;
            }

        } catch (Exception e) {
            logger.error("알림톡 전송 중 오류 발생: {}", e.getMessage(), e);
            return false;
        }
    }
}
