package ddog.daengleserver.global.auth.config;

import com.siot.IamportRestClient.IamportClient;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IamPortConfig {

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public IamportClient iamportClient() {
        String apiKey = dotenv.get("IAMPORT_API_KEY");
        String secretKey = dotenv.get("IAMPORT_SECRET_KEY");
        return new IamportClient(apiKey, secretKey);
    }
}
