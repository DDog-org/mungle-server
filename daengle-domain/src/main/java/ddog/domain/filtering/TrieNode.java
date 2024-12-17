package ddog.domain.filtering;

import java.util.*;

public class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    TrieNode fail;
    Map<String, String> outputs = new HashMap<>();
}