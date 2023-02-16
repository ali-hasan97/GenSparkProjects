import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class HumansVsGoblinsGUITest {
    Humanoid humanoid;
    Goblin goblin;
    Human human;

    @BeforeEach
    void setUp() {
        humanoid = new Humanoid();
        goblin = new Goblin();
        human = new Human();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getHealth() {
        humanoid.setHealth(50);
        assertEquals(50, humanoid.getHealth(), "Getter or setter failed with a reasonable integer.");

        humanoid.setHealth(100);
        assertEquals(100, humanoid.getHealth(), "Getter or setter failed with 100 health.");

        humanoid.setHealth(0);
        assertEquals(0, humanoid.getHealth(), "Getter or setter failed with 0 health.");
    }

    @Test
    void getArmor() {
        humanoid.setArmor(1);
        assertEquals(1, humanoid.getArmor(), "Getter or setter failed with 1 armor.");

        humanoid.setArmor(90);
        assertEquals(90, humanoid.getArmor(), "Getter or setter failed with 90 armor.");

        humanoid.setArmor(9);
        assertEquals(9, humanoid.getArmor(), "Getter or setter failed with 9 armor.");
    }

    @Test
    void getPosition() {
        humanoid.setPosition(0, 0);
        ArrayList<Integer> exp1 = new ArrayList<>();
        exp1.add(0);
        exp1.add(0);
        assertEquals(exp1, humanoid.getPosition(), "Getter or setter failed with [0, 0] position.");

        humanoid.setPosition(79, 99);
        ArrayList<Integer> exp2 = new ArrayList<>();
        exp2.add(79);
        exp2.add(99);
        assertEquals(exp2, humanoid.getPosition(), "Getter or setter failed with [79, 99] position.");

        humanoid.setPosition("n");
        exp2.clear();
        exp2.add(78);
        exp2.add(99);
        assertEquals(exp2, humanoid.getPosition(), "Getter or setter failed moving North.");

        humanoid.setPosition("e");
        exp2.clear();
        exp2.add(78);
        exp2.add(100);
        assertEquals(exp2, humanoid.getPosition(), "Getter or setter failed moving East.");

        humanoid.setPosition("w");
        exp2.clear();
        exp2.add(78);
        exp2.add(99);
        assertEquals(exp2, humanoid.getPosition(), "Getter or setter failed moving West.");

        humanoid.setPosition("s");
        exp2.clear();
        exp2.add(79);
        exp2.add(99);
        assertEquals(exp2, humanoid.getPosition(), "Getter or setter failed moving South.");
    }

    @Test
    void testToString() {
        String expString = "class Humanoid\nhealth 0\narmor 9\nposition [79, 99]";
    }

    @Test
    void Attack() {
        human.setHealth(100);
        human.setArmor(5);

        goblin.setHealth(100);
        goblin.setArmor(1);
        for (int i = 0; i < 500; i++) {
            assertTrue(0 <= human.attack(goblin) && human.attack(goblin) <= 100);
            assertTrue(0 <= goblin.attack(human) && goblin.attack(human) <= 20);
        }
    }
}