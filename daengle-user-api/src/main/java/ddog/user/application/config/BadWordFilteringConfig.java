package ddog.user.application.config;

import com.vane.badwordfiltering.BadWordFiltering;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BadWordFilteringConfig {

    @Bean
    public BadWordFiltering badWordFiltering() {
        return new BadWordFiltering();
    }
}
