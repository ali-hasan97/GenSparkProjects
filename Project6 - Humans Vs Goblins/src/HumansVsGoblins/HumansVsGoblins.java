import javax.swing.*;
import java.util.*;

public class HumansVsGoblins {
    public static void main(String[] args) {

        Human human = new Human();
        human.setHealth(100);
        human.setArmor(5);
        human.setPosition(0, 0);

        Goblin[] goblins = new Goblin[4];

        HashSet<ArrayList<Integer>> takenPositions = new HashSet<>();
        for (int i = 0; i < goblins.length; i++) {
            Goblin goblin = new Goblin();
            goblin.setHealth(100);
            goblin.setArmor(1);

            do {
                int a = new Random().nextInt(6);
                int b = new Random().nextInt(6);
                goblin.setPosition(a, b);
            } while (goblin.getPosition().equals(human.getPosition()) || takenPositions.contains(goblin.getPosition()));
            takenPositions.add(goblin.getPosition());
            goblins[i] = goblin;
        }

        System.out.println(human);
        System.out.println(Arrays.toString(goblins));

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Grid(goblins, human);
            }
        });
    }
}