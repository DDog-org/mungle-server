import com.vane.badwordfiltering.BadWordFiltering;
import ddog.UserApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest(classes = UserApplication.class)
@ExtendWith(SpringExtension.class)
public class ReviewBanWordFilteringTest {

    private static BadWordFiltering badWordFiltering;
    private static final String BANWORDS_FILE_PATH = "src/test/resources/banwords.txt";

    @BeforeAll
    public static void setup() {
        badWordFiltering = new BadWordFiltering();
        List<String> banWords = loadReviewsFromFile(BANWORDS_FILE_PATH);
        // 금칙어 추가
        badWordFiltering.addAll(banWords);
    }

    @Test
    public void testIsContainBanWord_noBanWord() {
        // Arrange
        String content = "이 미용사는 정말 친절했어요!";

        // Act
        boolean result = this.isContainBanWord(content);

        // Assert
        Assertions.assertFalse(result, "금칙어가 없는 경우 필터링을 통과");
    }

    @Test
    public void testIsContainBanWord_withBanWord() {
        // Arrange
        String content = "이 미용사는 정말 병신 같아요!";

        // Act
        boolean result = this.isContainBanWord(content);

        // Assert
        Assertions.assertTrue(result, "금칙어가 포함된 경우 필터링이 적용");
    }

    private boolean isContainBanWord(String content) {
        String filteredContent = badWordFiltering.change(content, new String[]{"_", ",", ".", "!", "?", "@", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " "});
        System.out.println("TEXT: " + content);
        return !content.equals(filteredContent);
    }

    @Test
    public void testCleanReviews() {
        // Arrange
        List<String> cleanReviews = loadReviewsFromFile("src/test/resources/cleanReviews.txt");

        int passCount = 0; // 통과 리뷰 수
        int failCount = 0; // 실패 리뷰 수

        // Act & Assert
        for (String review : cleanReviews) {
            boolean result = this.isContainBanWord(review);

            // 결과 출력
            if (!result) {
                ++passCount;
                System.out.printf("[PASS] Clean Review: '%s' -> Contains Ban Word: %b%n", review, result);
            } else {
                ++failCount;
                System.out.printf("[FAIL] Clean Review: '%s' -> Contains Ban Word: %b%n", review, result);
            }
            System.out.printf("\nTotal Clean Reviews: %d | Passed: %d | Failed: %d%n", cleanReviews.size(), passCount, failCount);

            Assertions.assertFalse(result, "욕설이 포함되지 않은 경우, 금칙어 필터링을 통과해야 함");
        }
    }

    @Test
    public void testBadReviews() {
        // Arrange
        List<String> badReviews = loadReviewsFromFile("src/test/resources/badReviews.txt");

        // Act & Assert
        for (String review : badReviews) {
            boolean result = this.isContainBanWord(review);
            System.out.printf("Bad Review: '%s' -> Contains Ban Word: %b%n", review, result);
            Assertions.assertTrue(result, "욕설이 포함된 경우, 금칙어 필터링이 적용되어야 함");
        }
    }

    private static List<String> loadReviewsFromFile(String filePath) {
        List<String> banWords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) { // 빈 줄 제외
                    banWords.add(line.trim());
                }
            }
        } catch (IOException e) {
            Assertions.fail("금칙어 파일을 읽는 중 오류가 발생했습니다: " + e.getMessage());
        }
        return banWords;
    }
}