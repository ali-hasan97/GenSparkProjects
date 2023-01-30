import javax.swing.*;

public class HumansVsGoblins {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        Goblin goblin = new Goblin();
        goblin.setHealth(25);
        goblin.setPosition("s");
        goblin.setPosition("e");

        Human human = new Human();
        human.setHealth(50);

        goblin.getHealth();
        human.getHealth();

        System.out.println(goblin);
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Grid(goblin, human);
            }
        });
    }
}