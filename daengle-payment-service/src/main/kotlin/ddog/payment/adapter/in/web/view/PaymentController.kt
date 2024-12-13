package ddog.payment.adapter.`in`.web.view

import ddog.common.WebAdapter
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@Controller
@WebAdapter
class PaymentController {

    @GetMapping("/success")
    fun successPage(): Mono<String> {

        System.out.println("success page !~!!!!!! \n\n\n\n")

        return Mono.just("success")
    }

    @GetMapping("/fail")
    fun failurePage(): Mono<String> {

        System.out.println("failure page !~!!!!!!!! \n\n\n\n")

        return Mono.just("fail")
    }
}