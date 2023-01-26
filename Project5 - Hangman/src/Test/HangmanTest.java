import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
class HangmanTest {

    Hangman hangman;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        hangman = new Hangman();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void generateWord() {
        for (int i = 0; i < 100; i++) {
            String word = hangman.generateWord();
            assertTrue(
                    word.equals("catinthehat") ||
                            word.equals("bullseye") ||
                            word.equals("daredevil") ||
                            word.equals("arsenal") ||
                            word.equals("barcelona") ||
                            word.equals("mustang") ||
                            word.equals("stingray") ||
                            word.equals("sloth") ||
                            word.equals("koala"));
            }
        }

    @org.junit.jupiter.api.Test
    void updateCurrWord() {
        assertTrue(hangman.updateCurrWord("b_ahblah", 1, "l").equals("blahblah"));
        assertTrue(hangman.updateCurrWord("____", 0, "m").equals("m___"));
        assertTrue(hangman.updateCurrWord("abcde_", 5, "f").equals("abcdef"));
    }

    @org.junit.jupiter.api.Test
    void checkGuess() {
        // Correct guess
        String[] expected1 = new String[] {"bl_hbl_h", "eio", "3", "O", "---", "|", " ", " "};
        String[] actual1 = hangman.checkGuess("l", "blahblah", "b__hb__h", "eio", 3, "O", "---", "|", " ", " ");
        assertArrayEquals(expected1, actual1);

        // Incorrect guess
        String[] expected2 = new String[] {"b__hb__h", "eiou", "4", "O", "---", "|", "/", " "};
        String[] actual2 = hangman.checkGuess("u", "blahblah", "b__hb__h", "eio", 3, "O", "---", "|", " ", " ");
        assertArrayEquals(expected2, actual2);
    }

}