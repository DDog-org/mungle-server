import ddog.domain.filter.BanWordValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class BanwordValidatorTest {

    private static BanWordValidator validator;

    @BeforeAll
    public static void setup() {
        validator = new BanWordValidator();
        // 금칙어 추가
        validator.addBanWord("병신");
        validator.addBanWord("시발");
        validator.addBanWord("바보");
        validator.addBanWord("시바");
        validator.addBanWord("졸라");

        // 허용어 추가
        validator.addAllowedWord("졸라맨");
        validator.addAllowedWord("병신년");
        validator.addAllowedWord("시바견");
        validator.addAllowedWord("고르곤졸라");
    }

    @Test
    public void testFindBanWords_withNormalizedContent() {
        String content = "이 미용사는 정말 병.신 같아요! 시 발!";
        Set<String> result = validator.findAllBanWords(content);
        Assertions.assertEquals(2, result.size(), "정규화 후 금칙어는 2개 발견되어야 함");
        Assertions.assertTrue(result.contains("병신"), "금칙어 '병신'이 포함되어야 함");
        Assertions.assertTrue(result.contains("시발"), "금칙어 '시발'이 포함되어야 함");
    }

    @Test
    public void testFindBanWords_withAllowedWord() {
        String content = "우리 집 시바견은 정말 귀여워요!";
        Set<String> result = validator.findAllBanWords(content);

        System.out.println(result);

        Assertions.assertTrue(result.isEmpty(), "허용어가 포함된 경우 금칙어 리스트는 비어 있어야 함");
    }
}