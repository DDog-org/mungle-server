package ddog.domain.filtering;

public class ContentNormalizer {
    public static String normalize(String content) {
        return content.replaceAll("[^가-힣a-zA-Z]", "");
    }
}