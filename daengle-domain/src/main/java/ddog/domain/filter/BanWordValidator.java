package ddog.domain.filter;

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
        String normalizedContent = ContentNormalizer.normalize(content);

        for (String banWord : banWords) {
            if (kmpMatcher.search(normalizedContent, banWord) && !isAllowWord(normalizedContent, banWord)) {
                return true;
            }
        }
        return false;
    }

    public Set<String> findAllBanWords(String content) {
        Set<String> detectedBanWords = new HashSet<>();
        String normalizedContent = ContentNormalizer.normalize(content);

        for (String banWord : banWords) {
            if (kmpMatcher.search(normalizedContent, banWord) && !isAllowWord(normalizedContent, banWord)) {
                detectedBanWords.add(banWord);
            }
        }
        return detectedBanWords;
    }

    public String getBanWords(String content) {
        String normalizedContent = ContentNormalizer.normalize(content);

        for (String banWord : banWords) {
            if (kmpMatcher.search(normalizedContent, banWord) && !isAllowWord(normalizedContent, banWord)) {
                return banWord;
            }
        }
        return null;
    }

    private boolean isAllowWord(String normalizedContent, String banWord) {
        for (String allowedWord : allowWords) {
            // 허용어가 금칙어보다 우선 적용되는지 확인
            if (kmpMatcher.search(normalizedContent, allowedWord)) {
                if (normalizedContent.contains(allowedWord) && allowedWord.contains(banWord)) {
                    return true; // 허용어가 금칙어를 포함한 경우
                }
            }
        }
        return false;
    }
}