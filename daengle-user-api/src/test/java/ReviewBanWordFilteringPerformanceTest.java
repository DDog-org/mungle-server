import com.vane.badwordfiltering.BadWordFiltering;
import ddog.UserApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = UserApplication.class)
@ExtendWith(SpringExtension.class)
public class ReviewBanWordFilteringPerformanceTest {

    private final BadWordFiltering badWordFiltering = new BadWordFiltering();

    @Test
    public void testPerformance_maxLengthInput() {
        // Arrange: 최대 400자의 리뷰 텍스트 생성
        String content = "이 미용사는 정말 병신 같아요! ".repeat(20);
        Assertions.assertTrue(content.length() <= 400, "입력 텍스트는 400자를 초과할 수 없습니다.");
        int iterations = 1000; // 1,000번 반복

        // Act: 400자 텍스트 처리 성능 측정
        long startTime = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            this.isContainBanWord(content);
        }
        long endTime = System.nanoTime();

        long duration = endTime - startTime;

        // Assert
        System.out.println("Max length input performance: " + (duration / iterations) + " ns per iteration");
        Assertions.assertTrue(duration / iterations < 2_000_000, "400자 리뷰는 평균 1ms 이내에 처리되어야 함");
    }

    private boolean isContainBanWord(String content) {
        String filteredContent = badWordFiltering.change(content, new String[] {"_", ",", ".", "!", "?", "@", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " "});
        return !content.equals(filteredContent);
    }
}