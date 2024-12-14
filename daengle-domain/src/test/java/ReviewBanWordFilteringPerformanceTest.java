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

public class ReviewBanWordFilteringPerformanceTest {

    private static BanWordValidator banWordValidator;

    @BeforeAll
    public static void setup() throws IOException {
        Set<String> banWords = loadWordsFromResource("badWords.txt");
        Set<String> allowWords = loadWordsFromResource("allowWords.txt");
        banWordValidator = new BanWordValidator(banWords, allowWords);
    }

    @Test
    public void testPerformance_maxLengthInput() {
        // Arrange: 최대 400자의 리뷰 텍스트 생성
        String content = "이 미용사는 정말 병신 같아요!짜증나".repeat(20); // 총 400자
        int contentLength = content.length();
        Assertions.assertTrue(contentLength <= 400, "입력 텍스트는 500자를 초과할 수 없습니다.");
        int iterations = 1000; // 1,000번 반복

        // Act: 성능 측정 시작
        long startTime = System.nanoTime();

        for (int i = 0; i < iterations; i++) {
            banWordValidator.findAllBanWords(content);
        }
        long endTime = System.nanoTime();

        // Calculate Performance
        long totalDurationNs = endTime - startTime;
        double averageDurationMs = (totalDurationNs / 1_000_000.0) / iterations;

        // Print Results
        System.out.println("=== Performance Test Results ===");
        System.out.printf("Input Text Length: %d characters%n", contentLength);
        System.out.printf("Iterations: %d%n", iterations);
        System.out.printf("Total Duration: %.3f ms%n", totalDurationNs / 1_000_000.0);
        System.out.printf("Average Duration per Iteration: %.3f ms%n", averageDurationMs);

        // Assert
        Assertions.assertTrue(averageDurationMs < 2, "최대 글자 리뷰 필터링 속도도 평균 2ms 이내에 처리되어야 함");
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