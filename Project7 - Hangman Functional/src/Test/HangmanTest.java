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
                    word.equals("throttle") ||
                            word.equals("bullseye") ||
                            word.equals("daredevil") ||
                            word.equals("arsenal") ||
                            word.equals("mississippi") ||
                            word.equals("mustang") ||
                            word.equals("stingray") ||
                            word.equals("sloth") ||
                            word.equals("koala"));
            }
        }

    @org.junit.jupiter.api.Test
    void updateCurrWord() {
        assertEquals("blahblah", Hangman.updateCurrWord("b_ahblah", 1, "l"));
        assertEquals("m___", Hangman.updateCurrWord("____", 0, "m"));
        assertEquals("abcdef", Hangman.updateCurrWord("abcde_", 5, "f"));
    }

    @org.junit.jupiter.api.Test
    void checkGuess() {
        // Correct guess
        String[] expected0 = new String[] {"test_ng", "ol", "2", "0000i00"};
        String[] actual0 = Hangman.checkGuess("n", "testing", "test__g", "ol", 2, "0000in0");
        assertArrayEquals(expected0, actual0);

        // Correct guess - duplicate letter
        String[] expected1 = new String[] {"bl_hb__h", "eio", "3", "00a00la0"};
        String[] actual1 = Hangman.checkGuess("l", "blahblah", "b__hb__h", "eio", 3, "0la00la0");
        assertArrayEquals(expected1, actual1);

        // Incorrect guess
        String[] expected2 = new String[] {"b__hb__h", "eiou", "4", "0__00__0"};
        String[] actual2 = Hangman.checkGuess("u", "blahblah", "b__hb__h", "eio", 3, "0__00__0");
        assertArrayEquals(expected2, actual2);
    }

}