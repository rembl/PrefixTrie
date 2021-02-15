import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrefixTrieTest {

    PrefixTrie tree = new PrefixTrie();

    @org.junit.jupiter.api.Test
    void searchWord() {
        tree.put("apple");
        tree.put("pear");
        tree.put("orange");
        tree.delete("orange");

        assertTrue(tree.searchWord("apple"));
        assertTrue(tree.searchWord("pear"));
        assertFalse(tree.searchWord("orange"));
        assertFalse(tree.searchWord("apples"));
        assertFalse(tree.searchWord("pea"));
        assertFalse(tree.searchWord("tangerine"));
    }

    @org.junit.jupiter.api.Test
    void searchWordsWithPrefix() {
        tree.put("apple");
        tree.put("pear");
        tree.put("pea");
        tree.put("peach");
        tree.put("orange");
        tree.put("orchid");
        tree.delete("orange");

        assertEquals(tree.searchWordsWithPrefix("pea"), List.of("pea", "pear","peach"));
        assertEquals(tree.searchWordsWithPrefix("or"), List.of("orchid"));
        assertEquals(tree.searchWordsWithPrefix("apple"), List.of("apple"));
        assertEquals(tree.searchWordsWithPrefix("ora"), List.of());
        assertEquals(tree.searchWordsWithPrefix("fruit"), List.of());
    }
}