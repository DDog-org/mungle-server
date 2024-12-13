package ddog.payment.adapter.out.web.toss.executor

import io.netty.handler.timeout.TimeoutException
import jdk.jfr.DataAmount
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class TossPaymentExecutor(
    private val tossPaymentWebClient: WebClient,
    private val uri: String = "/v1/payments/confirm"
) {

    fun execute(paymentKey: String, orderId: String, amount: String): Mono<String> {
        return tossPaymentWebClient.post()
            .uri(uri)
            .bodyValue(
                """
        {
          "paymentKey": "${paymentKey}",
          "orderId": "${orderId}", 
          "amount": ${amount}
        }
      """.trimIndent()
            )
            .retrieve()
            .bodyToMono(String::class.java)
    }
}