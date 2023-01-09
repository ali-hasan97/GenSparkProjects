import java.util.*;
public class Main {
    public static int generateNum() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }
    public static void main(String[] args) {
        System.out.println("Hello! What is your name?");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println("Well, " + name + ", I am thinking of a number between 1 and 20.\n" +
                "Take a guess. You have 6 attempts.");
        int guess = sc.nextInt();
        int count = 1;

        int randomNum = generateNum();

        while (count < 6) {
            if (guess > randomNum) {
                System.out.println("Your guess is too high. Guess again.");
                count++;
                guess = sc.nextInt();
            }
            else if (guess < randomNum) {
                System.out.println("Your guess is too low. Guess again.");
                count++;
                guess = sc.nextInt();
            }
            if (guess == randomNum){
                System.out.println("Good job, " + name + "! You guessed my number in " + count + " guesses!\n" +
                        "Would you like to play again? (y or n)");
                sc.nextLine(); // this is done to throw away away the \n not consumed by nextInt
                String rematch = sc.nextLine();
                if (rematch.equals("n")) break;
                else {
                    count = 1;
                    randomNum = generateNum();
                    System.out.println("The answer has been changed and your guess count has been reset. Enter a new guess:");
                    guess = sc.nextInt();
                }
            }
        }
    }
}