import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrefixTrie {

    public static class Node {
        private final Map<Character, Node> children = new HashMap<>();
        private boolean leaf;

        public void setLeaf(boolean leaf) {
            this.leaf = leaf;
        }
    }

    private final Node root = new Node();

    public void put(String input) {
        Node current = root;

        if (input == null || input.length() == 0) return;

        for (char letter : input.toCharArray()) {
            if (!current.children.containsKey(letter)) {
                current.children.put(letter, new Node());
            }
            current = current.children.get(letter);
        }

        current.setLeaf(true);
    }

    public void delete(String input) {
        Node current = root;
        Node lastNode = new Node();
        char lastLetter = 0;

        if (input == null || input.length() == 0) return;

        for (char letter : input.toCharArray()) {
            if (!current.children.containsKey(letter)) return;
            if (current.leaf || current.children.size() > 1) {
                lastNode = current;
                lastLetter = letter;
            }
            current = current.children.get(letter);
        }

        if (!current.leaf) return;
        if (!current.children.isEmpty()) {
            current.setLeaf(false);
        } else {
            lastNode.children.remove(lastLetter);
        }
    }

    public boolean searchWord(String input) {
        Node current = root;

        if (input == null || input.length() == 0) return false;

        for (char letter : input.toCharArray()) {
            if (!current.children.containsKey(letter)) return false;
            current = current.children.get(letter);
        }
        return current.leaf;
    }

    public List<String> searchWordsWithPrefix(String input) {
        List<String> allWords = new ArrayList<>();
        Node current = root;
        StringBuilder word = new StringBuilder();

        if (input == null || input.length() == 0) return allWords;

        for (char letter : input.toCharArray()) {
            if (current.children.containsKey(letter)) {
                word.append(letter);
                current = current.children.get(letter);
            } else return allWords;
        }

        if (current.leaf) allWords.add(String.valueOf(word));

        if (!current.children.isEmpty()) {
            childrenLoop(current, word, allWords);
        }

        return allWords;
    }

    public void childrenLoop(Node current, StringBuilder word, List<String> allWords) {
        if (!current.children.isEmpty()) {
            for (Character letter : current.children.keySet()) {
                word.append(letter);
                if (current.children.get(letter).leaf) {
                    allWords.add(String.valueOf(word));
                }
                childrenLoop(current.children.get(letter), word, allWords);
                word.deleteCharAt(word.length() - 1);
            }
        }
    }
}