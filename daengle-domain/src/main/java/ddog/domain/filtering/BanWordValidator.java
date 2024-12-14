package ddog.domain.filtering;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BanWordValidator {

    private final Set<String> banWords;
    private final Set<String> allowWords;
    private final KMPMatcher kmpMatcher;

    public BanWordValidator(Set<String> banWords, Set<String> allowWords) {
        this.banWords = banWords;
        this.allowWords = allowWords;
        this.kmpMatcher = new KMPMatcher(); // KMP 알고리즘 클래스 활용
    }

    public boolean isContainBanWord(String content) {
        String normalizedContent = normalizeAndRemoveAllowedWords(content);

        for (String banWord : banWords) {
            if (kmpMatcher.search(normalizedContent, banWord)) {
                return true;
            }
        }
        return false;
    }

    public Set<String> findAllBanWords(String content) {
        Set<String> detectedBanWords = new HashSet<>();
        String normalizedContent = normalizeAndRemoveAllowedWords(content);

        for (String banWord : banWords) {
            if (kmpMatcher.search(normalizedContent, banWord)) {
                detectedBanWords.add(banWord);
            }
        }
        return detectedBanWords;
    }

    public String getBanWords(String content) {
        String normalizedContent = normalizeAndRemoveAllowedWords(content);

        for (String banWord : banWords) {
            if (kmpMatcher.search(normalizedContent, banWord)) {
                return banWord;
            }
        }
        return null;
    }

    /**
     * 허용어를 제거한 뒤 정규화된 콘텐츠 반환
     */
    private String normalizeAndRemoveAllowedWords(String content) {
        String normalizedContent = ContentNormalizer.normalize(content);

        for (String allowedWord : allowWords) {
            if (kmpMatcher.search(normalizedContent, allowedWord)) {
                normalizedContent = normalizedContent.replaceAll(allowedWord, "");
            }
        }

        return normalizedContent;
    }
}