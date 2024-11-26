package ddog.daengleserver.notify;

import ddog.daengleserver.presentation.notify.dto.NotificationReq;
import ddog.daengleserver.presentation.notify.enums.NotifyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotificationControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("SSE를 활용하여 실시간으로 메시지를 전달받을 수 있다.")
    @Test
    public void testReceiveSseMessages() {
        // given
        Long userId = 1L;

        // when
        String message = webTestClient.get()
                .uri("/api/notification/connection?userId=" + userId)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.TEXT_EVENT_STREAM)
                .returnResult(String.class)
                .getResponseBody()
                .take(1)
                .blockFirst();

        // then
        assertTrue(message.contains("CONNECTED"), "연결 실패");
        System.out.println(message);
    }

    @DisplayName("알림 메시지를 서버에서 트리거하고 이를 SSE로 받을 수 있다.")
    @Test
    void testTriggerNotificationAndReceiveSse() {
        // given
        Long userId = 2L;
        String notificationMessage = "테스트 알림 메시지";
        NotifyType notifyType = NotifyType.CERTIFIED;

        // SSE 연결
        Flux<String> sseStream = webTestClient.get()
                .uri("/api/notification/connection?userId=" + userId)
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().isOk()
                .returnResult(String.class)
                .getResponseBody();

        // when
        webTestClient.post()
                .uri("/api/notification/send")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new NotificationReq(notifyType, notificationMessage, userId))
                .exchange()
                .expectStatus().isOk();

        // then
        sseStream.take(2)
                .doOnNext(message -> {
                    if (message.contains("CONNECTED")) {
                        assertTrue(message.contains("CONNECTED"), "연결 실패");
                        System.out.println(message);
                    } else if (message.contains(notificationMessage)) {
                        assertTrue(message.contains(notificationMessage), "알림 실패");
                        System.out.println(message);
                    }
                })
                .blockLast();
    }
}