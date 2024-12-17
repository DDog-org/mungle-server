package ddog.domain.filtering;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class BanWordValidator {

    private final AhoCorasickMatcher matcher;
    private final ContentNormalizer normalizer;

    public BanWordValidator(Set<String> banWords, Set<String> allowWords) {
        this.matcher = new AhoCorasickMatcher();
        this.normalizer = new ContentNormalizer();

        loadWords(banWords, allowWords);
    }

    private void loadWords(Set<String> banWords, Set<String> allowWords) {
        banWords.forEach(word -> matcher.addKeyword("banWord", word));
        allowWords.forEach(word -> matcher.addKeyword("allowWord", word));
        matcher.buildFailureLinks();
    }

    public String findBanWords(String content) {
        List<int[]> outputWordPositionDatas = new ArrayList<>();
        List<String> outputWords = new ArrayList<>();

        String normalizedContent = ContentNormalizer.normalize(content);
        matcher.search(normalizedContent, outputWordPositionDatas, outputWords);

        List<String> banWords = filterAllowWords(outputWordPositionDatas, outputWords);
        return banWords.isEmpty() ? null : banWords.get(0);
    }

    public boolean checkBanWord(String content) {
        List<int[]> outputWordPositionDatas = new ArrayList<>();
        List<String> outputWords = new ArrayList<>();

        String normalizedContent = ContentNormalizer.normalize(content);
        matcher.search(normalizedContent, outputWordPositionDatas, outputWords);

        return !filterAllowWords(outputWordPositionDatas, outputWords).isEmpty();
    }

    public List<String> filterAllowWords(List<int[]> outputWordPositionDatas, List<String> outputWords) {
        List<String> banWords = new ArrayList<>();
        Queue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            //시작위치가 같은 경우에는 허용어가 먼저 뽑혀서 그 아래 깔리는 비속어를 잡아 먹어야함
            if (o1[2] == o2[2]) {
                return Integer.compare(o1[1], o2[1]);
            }
            return Integer.compare(o1[2], o2[2]);
        });
        queue.addAll(outputWordPositionDatas);

        int allowWordLastPosition = -1;

        while (!queue.isEmpty()) {
            int[] outputWordPositionData = queue.poll();
            int wordStartPosition = outputWordPositionData[2];
            int wordEndPosition = outputWordPositionData[3];
            boolean isBanWord = outputWordPositionData[1]==1;


            //비속어임
            if (isBanWord) {
                if(allowWordLastPosition < wordStartPosition) {
                    banWords.add(outputWords.get(outputWordPositionData[0]));
                    /*System.out.println(outputWords.get(outputWordPositionData[0]));
                    System.out.println("allowWordLastPosition : "+allowWordLastPosition +"  "+
                            "wordStartPosition: " + wordStartPosition + " wordEndPosition: " + wordEndPosition );*/
                }
                //허용어임
            } else {
                //허용어의 범위 기록
                allowWordLastPosition = Math.max(allowWordLastPosition, wordEndPosition);
            }
        }

        //banWords.addAll(outputWords);
        return banWords;
    }
}