import java.util.*;

public class Humanoid {
    private int health;
    private int armor; // attack magnitudes are divided by this number
    private ArrayList<Integer> position = new ArrayList<>();

    public int getHealth() { return health; }

    public int getArmor() {
        return armor;
    }

    public ArrayList<Integer> getPosition() {
        return position;
    }

    public void setHealth(int newHealth) { this.health = newHealth; }

    public void setArmor(int newArmor) {
        this.armor = newArmor;
    }

    public void setPosition(String key) {
        this.position = move(key);
    }

    public void setPosition(int a, int b) {
        this.position.clear();
        this.position.add(a);
        this.position.add(b);
    }

    public ArrayList<Integer> move(String key) {
        switch(key) {
            case("n") :
                position.set(0, position.get(0) - 1);
                return position;
            case("s") :
                position.set(0, position.get(0) + 1);
                return position;
            case("e") :
                position.set(1, position.get(1) + 1);
                return position;
            case("w") :
                position.set(1, position.get(1) - 1);
                return position;
            default :
                return position;
        }
    }

    public String toString() {
        String name = (this.getClass().toString().equals("class Human")) ? "Human" : "Goblin";
        return "\n" +
                name +
                ":\nhealth " + health +
                "\narmor " + armor +
                "\nposition " + position.toString();
    }
}
