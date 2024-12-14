import ddog.domain.filtering.BanWordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class ReviewBanWordFilteringAccuracyTest {

    private static BanWordValidator banWordValidator;

    @BeforeAll
    public static void setup() throws IOException {
        Set<String> banWords = loadWordsFromResource("badWords.txt");
        Set<String> allowWords = loadWordsFromResource("allowWords.txt");
        banWordValidator = new BanWordValidator(banWords, allowWords);
    }

    @Test
    public void testCleanReviews() throws IOException {
        // Arrange
        Set<String> cleanReviews = loadReviewsFromResource("cleanReviews.txt");

        // Act & Assert
        for (String review : cleanReviews) {
            boolean result = banWordValidator.isContainBanWord(review);
            System.out.printf("[PASS] Clean Review: '%s' -> Contains Ban Word: %b%n", review, result);
            Assertions.assertFalse(result, "클린 리뷰에는 금칙어가 없어야 함");
        }
    }

    @Test
    public void testBadReviews() throws IOException {
        // Arrange
        Set<String> badReviews = loadReviewsFromResource("badReviews.txt");

        // Act & Assert
        for (String review : badReviews) {
            boolean result = banWordValidator.isContainBanWord(review);
            System.out.printf("[FAIL] Bad Review: '%s' -> Contains Ban Word: %b%n", review, result);
            Assertions.assertTrue(result, "욕설이 포함된 경우, 금칙어 필터링이 적용되어야 함");
        }
    }

    private static Set<String> loadReviewsFromResource(String fileName) throws IOException {
        Set<String> reviews = new HashSet<>();
        ClassPathResource resource = new ClassPathResource(fileName);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    reviews.add(line.trim());
                }
            }
        }
        return reviews;
    }

    private static Set<String> loadWordsFromResource(String fileName) throws IOException {
        Set<String> words = new HashSet<>();
        ClassPathResource resource = new ClassPathResource(fileName);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
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