import com.vane.badwordfiltering.BadWordFiltering;
import ddog.UserApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = UserApplication.class)
@ExtendWith(SpringExtension.class)
public class ReviewBanWordFilteringTest {

    private final BadWordFiltering badWordFiltering = new BadWordFiltering();

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

    @Test
    public void testIsContainBanWord_withAllowedWord() {
        // Arrange
        String content = "우리 집 시바견은 정말 귀여워요!";

        // Act
        boolean result = this.isContainBanWord(content);

        // Assert
        Assertions.assertFalse(result, "허용어가 포함된 경우 필터링에서 제외되어야 합니다.");
    }

    private boolean isContainBanWord(String content) {
        String filteredContent = badWordFiltering.change(content, new String[] {"_", ",", ".", "!", "?", "@", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", " "});
        System.out.println("Original: " + content);
        System.out.println("Filtered: " + filteredContent);
        return !content.equals(filteredContent);
    }
}