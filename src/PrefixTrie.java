import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrefixTrie {

    public static class Node {
        private final Map<Character, Node> children = new HashMap<>();
        private boolean leaf;
        private String value;

        public void putLeaf(boolean leaf) {
            this.leaf = leaf;
        }
        public void putValue(String value) {
            this.value = value;
        }
    }

    public static class Trie {
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

            current.putLeaf(true);
            current.putValue(input);
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
                current.putLeaf(false);
                current.value = null;
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
            List<String> allWordsWithPrefix = new ArrayList<>();
            Node current = root;

            if (input == null || input.length() == 0) return allWordsWithPrefix;

            for (char letter : input.toCharArray()) {
                if (current.children.containsKey(letter)) {
                    current = current.children.get(letter);
                } else return allWordsWithPrefix;
            }

            if (!current.children.isEmpty()) {
                childrenLoop(current, allWordsWithPrefix);
            } else if (current.leaf) allWordsWithPrefix.add(current.value);

            return allWordsWithPrefix;
        }

        public void childrenLoop(Node current, List<String> allWordsWithPrefix) {
            if (current.leaf) allWordsWithPrefix.add(current.value);
            if (!current.children.isEmpty()) {
                for (Character letter : current.children.keySet()) {
                    childrenLoop(current.children.get(letter), allWordsWithPrefix);
                }
            }
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
        tree.put("ang");
        tree.put("out");
        tree.put("hello");
        tree.put("world");
        tree.delete("anger");
        tree.delete("ang");
        System.out.println(tree.root.children.keySet());
        System.out.println(tree.searchWord("apple"));
        System.out.println(tree.searchWord("ang"));
        System.out.println(tree.searchWord("ant"));
        System.out.println(tree.searchWordsWithPrefix("ang"));
        System.out.println(tree.searchWordsWithPrefix("a"));
        System.out.println(tree.searchWordsWithPrefix("hello"));
        System.out.println(tree.searchWordsWithPrefix("word"));
    }
}
