import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrefixTrie {

    public static class Node {
        private final Map<Character, Node> children = new HashMap<>();
        private boolean leaf;
        private StringBuilder value = new StringBuilder();

        public void putLeaf(boolean leaf) {
            this.leaf = leaf;
        }
    }

    public static class Trie {
        private final Node root = new Node();
        private final List<String> allWords = new ArrayList<>();

        public void put(String input) {
            Node current = root;
            StringBuilder word = new StringBuilder();

            if (input == null || input.length() == 0) return;

            for (char letter : input.toCharArray()) {
                word.append(letter);
                if (!current.children.containsKey(letter)) {
                    current.children.put(letter, new Node());
                }
                current = current.children.get(letter);
            }

            current.putLeaf(true);
            current.value = word;
            allWords.add(String.valueOf(word));
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
            if (!current.children.isEmpty()) current.putLeaf(false);
            else {
                lastNode.children.remove(lastLetter);
                lastNode.value = null;
            }
            allWords.remove(input);
        }

        public boolean search(String input) {
            return allWords.contains(input);
        }

        public List<String> searchAll(String input) {
            List<String> prefixWords = new ArrayList<>();
            for (String word : allWords) {
                if (word.startsWith(input)) prefixWords.add(word);
            }
            return prefixWords;
        }
    }

    public static void main(String[] args) {
        Trie tree = new Trie();
        tree.put("apple");
        tree.put("anger");
        tree.put("angers");
        tree.put("angle");
        tree.put("angel");
        tree.put("orange");
        tree.put("out");
        tree.delete("anger");
        System.out.println(tree.root.children.keySet());
        System.out.println(tree.search("apple"));
        System.out.println(tree.search("ant"));
        System.out.println(tree.searchAll("ang"));
    }
}
