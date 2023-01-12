import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DragonCaveTest {

    DragonCave dragonCave;

    String caveResponseInvalid = "1 or 2 not selected.";
    String caveResponse1 = "You approach the cave...\n" +
            "It is dark and spooky...\n" +
            "A large dragon jumps out in front of you! He opens his jaws and...\n" +
            "Gobbles you down in one bite!";

    String caveResponse2 = "You approach the cave...\n" +
            "It is dark and spooky...\n" +
            "A large dragon jumps out in front of you! He opens his jaws and...\n" +
            "says 'Hi, I'm a friendly dragon!'";


    @BeforeEach
    void setUp() {
        dragonCave = new DragonCave();
    }

    @Test
    void getInt() {
        assertAll("Test Props",
                () -> assertEquals(caveResponseInvalid,dragonCave.caveResponse(11),"positive int failure"),
                () -> assertEquals(caveResponseInvalid, dragonCave.caveResponse(0), "0 failure"),
                () -> assertEquals(caveResponseInvalid, dragonCave.caveResponse(-5), "negative failure"),
                () -> assertEquals(caveResponse1, dragonCave.caveResponse(1), "1 failure"),
                () -> assertEquals(caveResponse2, dragonCave.caveResponse(2), "2 failure"));
    }

    @AfterEach
    void tearDown() {
    }
}
