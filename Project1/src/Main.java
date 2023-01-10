import java.util.*;
public class Main {
    public static void main(String[] args)
    {
        System.out.println("You are in a land full of dragons. In front of you,\n" +
                "you see two caves. In one cave, the dragon is friendly\n" +
                "and will share his treasure with you. The other dragon\n" +
                "is greedy and hungry and will eat you on sight.\n" +
                "Which cave will you go into? (1 or 2)");

        Scanner sc = new Scanner(System.in);
        int num = 0;
        try {num = sc.nextInt();}
        catch (Exception e) {
            System.out.println("Invalid input.");
            return;
        }

        if (num == 1) {
            System.out.println("You approach the cave...\n" +
                    "It is dark and spooky...\n" +
                    "A large dragon jumps out in front of you! He opens his jaws and...\n" +
                    "Gobbles you down in one bite!");
        } else if (num == 2) {
            System.out.println("You approach the cave...\n" +
                    "It is dark and spooky...\n" +
                    "A large dragon jumps out in front of you! He opens his jaws and...\n" +
                    "says 'Hi, I'm a friendly dragon!'");
        } else System.out.println("1 or 2 not selected.");
    }
}
