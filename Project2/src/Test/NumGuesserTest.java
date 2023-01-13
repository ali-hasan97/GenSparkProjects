import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumGuesserTest {

    NumGuesser numGuesser;
    String low2 = "Your guess is too low. Guess again. 2 guesses left.";
    String high5 = "Your guess is too high. Guess again. 5 guesses left.";


    @BeforeEach
    void setUp() {
        numGuesser = new NumGuesser();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void guessResponse() {
        assertAll("Test responses",
            () ->  assertEquals(low2, numGuesser.guessResponse(5, 10, 4), "Failed too low."),
            () -> assertEquals(high5, numGuesser.guessResponse(16, 8, 1), "Failed too high."));
    }

    @Test
    void generateNum() {
       int randomNum = 0;
       for (int i = 0; i < 400; i++) {
           randomNum = numGuesser.generateNum();
           assertTrue(randomNum >= 1 && randomNum <= 20);
       }
    }
}