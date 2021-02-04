import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrefixTrie {

    public static class Node {
        private Map<Character, Node> children = new HashMap<>();
        private boolean leaf;

        public void putLeaf(boolean leaf) {
            this.leaf = leaf;
        }
    }

    public static class Trie {
        private Node root = new Node();

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
            else lastNode.children.remove(lastLetter);
        }

        public boolean search(String input) {
            Node current = root;

            if (input == null || input.length() == 0) return false;

            for (char letter : input.toCharArray()) {
                if (!current.children.containsKey(letter)) return false;
                current = current.children.get(letter);
            }
            return current.leaf;
        }

        public List<String> searchAll(String input) {
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

            while (!current.children.isEmpty()) {
                for (Character letter : current.children.keySet()) {
                    word.append(letter);
                    if (current.children.get(letter).leaf) allWords.add(String.valueOf(word));
                    childrenLoop(current.children.get(letter), word, allWords);
                }
            }

            return allWords;
        }

        public void childrenLoop(Node current, StringBuilder word, List<String> allWords) {
            while (!current.children.isEmpty()) {
                for (Character letter : current.children.keySet()) {
                    word.append(letter);
                    if (current.children.get(letter).leaf) allWords.add(String.valueOf(word));
                    if (!current.children.isEmpty()) childrenLoop(current.children.get(letter), word, allWords);
                    current = current.children.get(letter);
                }
            }
        }
    }

    public static void main(String[] args) {
        Trie tree = new Trie();
        tree.put("peepeepoopoo");
        tree.put("etop");
        tree.put("ehop");
        tree.put("eepee");
        tree.put("apeep");
        tree.delete("peepeepoopoo");
        System.out.println(tree.root.children);
        System.out.println(tree.search("peepeepoopoo"));
        System.out.println(tree.search("apeep"));
        System.out.println(tree.searchAll("e"));
    }
}
