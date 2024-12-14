package ddog.domain.filtering;

public class ContentNormalizer {

    private ContentNormalizer() {
        // Utility class, no instantiation
    }

    public static String normalize(String content) {
        return content.replaceAll("[^가-힣a-zA-Z]", "");
    }
}