import java.util.*;
public class Main {
    // generate word. Take input for guess. Check if guess correct or not. Handle correct. Handle incorrect. Print hangman. Print letters and missed letters.

    public static String generateWord() {
        // TODO: NEED TO UPDATE!
        return "catinthehat";
    }

    public static String updateCurrWord(String currWord, int idx, String guess) {
        String beg = currWord.substring(0, idx);
        String end = currWord.substring(idx + 1);
        currWord = beg + guess + end;
        return currWord;
    }
    public static void printMan(String head, String arms, String body, String leg1, String leg2, String missed) {
        System.out.println();
        System.out.println(" +----+\n " + head + "    |\n" + arms + "   |\n " + body + "    |\n" + leg1 + " " + leg2 + "  ===");
        System.out.println("Missed letters:" + missed);
    }

    public static String takeGuess() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a guess:");
        try {
            return sc.nextLine();
        } catch (Exception e) {
            return "Error: Invalid guess format";
        }
    }

    // check if guess correct or not and handle accordingly
    public static String[] checkGuess(String guess, String correctWord, String currWord, String missed, int wrongCount, String head, String arms, String body, String leg1, String leg2) {
        String prevWord = currWord;

        // check if guess is in correctWord, and update currWord if it is.
        for (int i = 0; i < correctWord.length(); i++) {
            if (guess.equals(Character.toString(correctWord.charAt(i)))) {
                currWord = updateCurrWord(currWord, i, guess);
            }
        }

        // if currWord is different from prevWord, it means the guess was correct, so return here
        if (!currWord.equals(prevWord)) {
            printMan(head, arms, body, leg1, leg2, missed);
            return new String[] {currWord, missed, String.valueOf(wrongCount), head, arms, body, leg1, leg2};
        }

        // if guess is not update wrongCount, missed, and the man's limbs
        wrongCount++;
        missed += guess;

        switch (wrongCount) {
            case (1) :
                head = "O";
                break;
            case (2) :
                arms = "---";
                break;
            case (3) :
                body = "|";
                break;
            case (4) :
                leg1 = "/";
                break;
            case (5) :
                leg2 = "\\";
                break;
        }
        printMan(head, arms, body, leg1, leg2, missed);
        return new String[] {currWord, missed, String.valueOf(wrongCount), head, arms, body, leg1, leg2};
    }
    public static void main(String[] args) {

        String head = " ";
        String arms = "   ";
        String body = " ";
        String leg1 = " ";
        String leg2 = " ";
        String guess = "";
        String missed = "";
        String currWord = "";
        int wrongCount = 0;

        System.out.println("H A N G M A N");
        System.out.println("You have 5 guesses before the man is hanged. Guess each letter of the word, one letter at a time.");
        printMan(head, arms, body, leg1, leg2, missed);
        String correctWord = generateWord();
        for (int i = 0; i < correctWord.length(); i++) currWord += "_";
        System.out.println(currWord);

        while (wrongCount < 5 && !currWord.equals(correctWord)) {
            guess = takeGuess();

            // keep prompting for guesses if guess has been used before or is invalid
            while (guess.length() != 1 || (missed.indexOf(guess.charAt(0)) != -1 || currWord.indexOf(guess.charAt(0)) != -1)) {
                String issue = "";
                if (guess.length() == 0) issue = "You have entered nothing. Enter a character.";
                else if (guess.length() > 1) issue = "You have entered more than one letter. Enter a single character only.";
                else issue = "You have already guessed that letter. Pick a different letter.";
                System.out.println(issue);
                guess = takeGuess();
            }

            // Update variables in main
            String[] outArray = checkGuess(guess, correctWord, currWord, missed, wrongCount, head, arms, body, leg1, leg2);
            currWord = outArray[0];
            missed = outArray[1];
            wrongCount = Integer.parseInt(outArray[2]);
            head = outArray[3];
            arms = outArray[4];
            body = outArray[5];
            leg1 = outArray[6];
            leg2 = outArray[7];
            System.out.println(currWord);
        }

        // win/lose statement
        if (currWord.equals(correctWord)) System.out.println("Nice work! You guessed correctly so no one was hanged!");
        else System.out.println("You've just let a man die by guessing incorrectly too many times. Shame on you.");
    }
}