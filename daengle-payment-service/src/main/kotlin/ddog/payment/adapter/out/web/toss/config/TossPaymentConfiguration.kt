package ddog.payment.adapter.out.web.toss.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TossPaymentConfiguration {

    @Value("\${PSP.toss.url}")
    private lateinit var baseUrl: String

    @Bean
    fun baseUrl(): String {
        return baseUrl
    }
}