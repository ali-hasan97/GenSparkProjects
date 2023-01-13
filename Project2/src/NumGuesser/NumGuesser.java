import java.util.*;
public class NumGuesser {
    public static int generateNum() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }

    public static int takeGuess(Scanner sc) {
        try {
            int guess = sc.nextInt();
            while (guess < 1 || guess > 20) {
                System.out.println("Guess must be within range of 1 to 20. Guess again.");
                guess = sc.nextInt();
            }
            return guess;
        } catch (Exception e) {
            System.out.println("Invalid guess. Enter an integer.");
            sc.nextLine(); // this is done to throw away the \n not consumed by nextInt
            return takeGuess(sc);
        }
    }

    public static String guessResponse(int guess, int randomNum, int count) {
        String response = "";
        if (guess > randomNum) {
            response = "Your guess is too high. Guess again. " + (6-count) + " guesses left.";
        }
        else {
            response = "Your guess is too low. Guess again. " + (6-count) + " guesses left.";
        }
        return response;
    }
    public static void main(String[] args) {
        System.out.println("Hello! What is your name?");
        Scanner sc = new Scanner(System.in);
        String name = "";
        try {
            name = sc.nextLine();
        } catch (Exception e) {
            System.out.println("Invalid input");
        }
        System.out.println("Well, " + name + ", I am thinking of a number between 1 and 20.\n" +
                "Take a guess. You have 6 attempts.");

        String rematch = "y";
        while (rematch.equals("y")) {
            int guess = takeGuess(sc);
            int randomNum = generateNum();
            int count = 1;

            while (count < 6 && guess != randomNum) {
                System.out.println(guessResponse(guess, randomNum, count));
                count++;
                guess = takeGuess(sc);
            }

            if (guess == randomNum || count == 6){
                if (guess == randomNum) System.out.println("Good job, " + name + "! You guessed my number in " + count + " guesses!");
                else System.out.println("Too many guesses. You lost :(");
                System.out.println("Would you like to play again? (y or n)");

                sc.nextLine(); // this is done to throw away the \n not consumed by nextInt
                try {
                    rematch = sc.nextLine();
                } catch (Exception e) {
                    System.out.println("Invalid input");
                }
                while (!rematch.equals("y") && !rematch.equals("n")) {
                    sc.nextLine(); // this is done to throw away the \n not consumed by nextInt
                    try {
                        rematch = sc.nextLine();
                    } catch (Exception e) {
                        System.out.println("Invalid input");
                    }
                }
                if (rematch.equals("n")) break;
                else {
                    System.out.println("The answer has been changed and your guess count has been reset. Enter a new guess:");
                }
            }
        }

        sc.close();
    }
}