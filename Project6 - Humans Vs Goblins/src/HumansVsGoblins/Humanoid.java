import java.util.Arrays;

public class Humanoid {
    private int health;
    private int[] position = new int[2];

    public int getHealth() {
        System.out.println(health);
        return health;
    }

    public int[] getPosition() {
        System.out.print(Arrays.toString(position));
        return position;
    }

    public void setHealth(int newHealth) {
        this.health = newHealth;
        System.out.println("Health set to " + this.health);
    }

    public void setPosition(String key) {
        this.position = move(key);
        System.out.println("Position set to " + Arrays.toString(this.position));
    }

    public int[] move(String key) {
        switch(key) {
            case("n") :
                System.out.println("n");
                position[0] -= 1;
                return position;
            case("s") :
                System.out.println("s");
                position[0] += 1;
                return position;
            case("e") :
                System.out.println("e");
                position[1] += 1;
                return position;
            case("w") :
                System.out.println("w");
                position[1] -= 1;
                return position;
            default :
                return position;
        }
    }

    public String toString() {
        return this.getClass().toString() +
                "\nhealth " + health +
                "\nposition " + Arrays.toString(position);
    }
}
