package ddog.user.application.config;

import ddog.domain.filter.BanWordValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.InputStream;
import java.io.InputStreamReader;


@Configuration
public class BadWordFilteringConfig {

    private static final String BAN_WORDS_FILE = "filter/banWords.txt";
    private static final String ALLOW_WORDS_FILE = "filter/allowWords.txt";

    @Bean
    public Set<String> banWords() throws IOException {
        return loadWordsFromResource(new ClassPathResource(BAN_WORDS_FILE));
    }

    @Bean
    public Set<String> allowWords() throws IOException {
        return loadWordsFromResource(new ClassPathResource(ALLOW_WORDS_FILE));
    }

    @Bean
    public BanWordValidator banWordValidator(Set<String> banWords, Set<String> allowWords) {
        return new BanWordValidator(banWords, allowWords);
    }

    private Set<String> loadWordsFromResource(Resource resource) throws IOException {
        Set<String> words = new HashSet<>();
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    words.add(line.trim());
                }
            }
        }
        return words;
    }
}