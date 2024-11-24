package ddog.daengleserver.global.auth.config;

import com.siot.IamportRestClient.IamportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamPortConfig {

    String apiKey = "5011548338023285";
    String secretKey = "isze63V3ec1ihXsUvkOrRsYy1d9l0qtDAgcdpNuc2Ldo3wFxarB9EdvQQ45vQjZInr3zTysnb5aOoWmF";

    @Bean
    public IamportClient iamportClient() {
        return new IamportClient(apiKey, secretKey);
    }
}
