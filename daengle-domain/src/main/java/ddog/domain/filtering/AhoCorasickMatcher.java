package ddog.domain.filtering;

import java.util.*;

public class AhoCorasickMatcher {

    private final TrieNode root = new TrieNode();

    public void addKeyword(String type, String keyword) {
        TrieNode node = root;
        for (char c : keyword.toCharArray()) {
            node = node.children.computeIfAbsent(c, k -> new TrieNode());
        }
        node.outputs.put(type, keyword);
    }

    public void buildFailureLinks() {
        Queue<TrieNode> queue = new LinkedList<>();
        root.fail = root;

        for (TrieNode node : root.children.values()) {
            node.fail = root;
            queue.add(node);
        }

        while (!queue.isEmpty()) {
            TrieNode current = queue.poll();
            for (Map.Entry<Character, TrieNode> entry : current.children.entrySet()) {
                char c = entry.getKey();
                TrieNode child = entry.getValue();
                queue.add(child);

                TrieNode failNode = current.fail;
                while (failNode != root && !failNode.children.containsKey(c)) {
                    failNode = failNode.fail;
                }
                child.fail = failNode.children.getOrDefault(c, root);
                child.outputs.putAll(child.fail.outputs);
            }
        }
    }

    public void search(String text, List<int[]> outputWordPositionData, List<String> outputWords) {
        TrieNode node = root;
        int endPosition = 0;

        for (char c : text.toCharArray()) {
            while (node != root && !node.children.containsKey(c)) {
                node = node.fail;
            }
            node = node.children.getOrDefault(c, root);

            for (Map.Entry<String, String> entry : node.outputs.entrySet()) {
                String word = entry.getValue();
                int startPosition = endPosition - word.length() + 1;
                int isBanWord = entry.getKey().equals("banWord") ? 1 : 0;
                outputWordPositionData.add(new int[]{outputWords.size(), isBanWord, startPosition, endPosition});
                outputWords.add(word);
            }
            endPosition++;
        }
    }
}