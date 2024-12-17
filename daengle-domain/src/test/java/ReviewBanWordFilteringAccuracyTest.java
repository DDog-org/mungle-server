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
        // 금칙어와 허용어 목록 로드
        Set<String> banWords = loadWordsFromResource("badWords.txt");
        Set<String> allowWords = loadWordsFromResource("allowWords.txt");

        // BanWordValidator 초기화
        banWordValidator = new BanWordValidator(banWords, allowWords);
    }

    @Test
    public void testCleanReviews() throws IOException {
        // Arrange: 클린 리뷰 목록 로드
        Set<String> cleanReviews = loadReviewsFromResource("cleanReviews.txt");

        // Act & Assert: 금칙어가 없어야 함
        for (String review : cleanReviews) {
            boolean result = banWordValidator.checkBanWord(review);
            System.out.printf("[PASS] Clean Review: '%s' -> Contains Ban Word: %b%n", review, result);
            Assertions.assertFalse(result, "클린 리뷰에는 금칙어가 없어야 함");
        }
    }

    @Test
    public void testBadReviews() throws IOException {
        // Arrange: 금칙어가 포함된 리뷰 목록 로드
        Set<String> badReviews = loadReviewsFromResource("badReviews.txt");

        // Act & Assert: 금칙어가 감지되어야 함
        for (String review : badReviews) {
            boolean result = banWordValidator.checkBanWord(review);
            System.out.printf("[FAIL] Bad Review: '%s' -> Contains Ban Word: %b%n", review, result);
            Assertions.assertTrue(result, "욕설이 포함된 리뷰는 금칙어 필터링이 적용되어야 함");
        }
    }

    @Test
    public void testAllowWords() throws IOException {
        // Arrange: 허용어가 포함된 리뷰 목록 로드
        Set<String> allowWordReviews = loadReviewsFromResource("cleanReviews.txt");

        // Act & Assert: 허용어는 금칙어로 처리되면 안됨
        for (String review : allowWordReviews) {
            boolean result = banWordValidator.checkBanWord(review);
            System.out.printf("[ALLOW] Allow Word Review: '%s' -> Contains Ban Word: %b%n", review, result);
            Assertions.assertFalse(result, "허용어가 포함된 경우 금칙어로 처리되면 안됨");
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