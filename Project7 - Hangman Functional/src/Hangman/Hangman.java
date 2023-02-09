import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;
import java.util.stream.*;
import java.nio.file.*;
public class Hangman {
    // generate word. Take input for guess. Check if guess correct or not. Handle correct. Handle incorrect. Print hangman. Print letters and missed letters.

    public static String generateWord() {
        String[] words = new String[] {"throttle", "mississippi", "daredevil", "bullseye", "mustang", "stingray", "arsenal", "sloth", "koala"};
        Random random = new Random();
        int rand = random.nextInt(words.length);
        return words[rand];
    }

    public static String updateCurrWord(String currWord, int idx, String guess) {
        String beg = currWord.substring(0, idx);
        String end = currWord.substring(idx + 1);
        currWord = beg + guess + end;
        return currWord;
    }
    public static void printMan(String head, String arms, String body, String leg1, String leg2, String missed, String currWord) {
        System.out.println();
        System.out.println(" +----+\n " + head + "    |\n" + arms + "   |\n " + body + "    |\n" + leg1 + " " + leg2 + "  ===");
        System.out.println("Missed letters:" + missed);
        System.out.println(currWord);
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
    public static String[] checkGuess(String guess, String correctWord, String currWord, String missed, int wrongCount, String head, String arms, String body, String leg1, String leg2, String remWord) {
        String prevWord = currWord;

        // check if guess is in correctWord, and update currWord if it is.
        int i = remWord.indexOf(guess);
        if (i != -1) {
            currWord = updateCurrWord(currWord, i, guess);
            if (i == 0) {
                remWord = "0" + remWord.substring(1);
            } else if (i == remWord.length() - 1) {
                remWord = remWord.substring(0, i) + "0";
            } else {
                remWord = remWord.substring(0, i) + "0" + remWord.substring(i + 1);
            }

            // if there is a duplicate letter, don't print anything and just go back to the main loop
            if (remWord.contains(guess)) {
                String[] strArray = new String[] {currWord, missed, String.valueOf(wrongCount), head, arms, body, leg1, leg2, remWord};
                return strArray;
            };
        }

        // if currWord is different from prevWord, it means the guess was correct, so return here
        if (!currWord.equals(prevWord)) {
            printMan(head, arms, body, leg1, leg2, missed, currWord);
            String[] strArray = new String[] {currWord, missed, String.valueOf(wrongCount), head, arms, body, leg1, leg2, remWord};
            return strArray;
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
        printMan(head, arms, body, leg1, leg2, missed, currWord);
        return new String[] {currWord, missed, String.valueOf(wrongCount), head, arms, body, leg1, leg2, remWord};
    }
    public static void main(String[] args) {

        String head = " ";
        String arms = "   ";
        String body = " ";
        String leg1 = " ";
        String leg2 = " ";
        String guess = " ";
        String missed = "";
        String currWord = "";
        String highScore = "";
        String numbers = "0123456789";
        int wrongCount = 0;

        try {
            FileWriter myWriter = new FileWriter("./src/Hangman/Record.txt", true);
            BufferedWriter bw = new BufferedWriter(myWriter);
            bw.newLine();
            bw.write("testing");
            bw.close();
            List<String> scores = Files.readAllLines(Paths.get("./src/Hangman/Record.txt"), StandardCharsets.UTF_8);
            System.out.println(scores);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("H A N G M A N");
        System.out.println("You have 5 guesses before the man is hanged. Guess each letter of the word, one letter at a time.");
        printMan(head, arms, body, leg1, leg2, missed, currWord);
        String correctWord = generateWord();
        String remWord = correctWord;
        currWord = correctWord.codePoints().mapToObj(c -> "_").collect(Collectors.joining());
        System.out.println(currWord);

        // main game loop
        while (wrongCount < 5 && !currWord.equals(correctWord)) {

            // handle duplicate letters
            if (remWord.contains(guess)) {
                int i = remWord.indexOf(guess);
                currWord = updateCurrWord(currWord, i, guess);
                if (i == 0) {
                    remWord = "0" + remWord.substring(1);
                } else if (i == remWord.length() - 1) {
                    remWord = remWord.substring(0, i) + "0";
                } else {
                    remWord = remWord.substring(0, i) + "0" + remWord.substring(i + 1);
                }
                if (remWord.contains(guess)) {
                    continue;
                } else {
                    guess = " ";
                    printMan(head, arms, body, leg1, leg2, missed, currWord);
                }
            }

            guess = takeGuess();

            // keep prompting for guesses if guess has been used before or is invalid
            if (guess.length() != 1 || (missed.indexOf(guess.charAt(0)) != -1 || numbers.contains(guess) || currWord.indexOf(guess.charAt(0)) != -1)) {
                String issue = "";
                if (guess.length() == 0) issue = "You have entered nothing. Enter a character.";
                else if (guess.length() > 1) issue = "You have entered more than one letter. Enter a single character only.";
                else if (numbers.contains(guess)) issue = "The word does not contain numbers. Pick a letter.";
                else issue = "You have already guessed that letter. Pick a different letter.";
                System.out.println(issue);
                guess = " ";
                continue;
            }

            // Update variables in main
            String[] outArray = checkGuess(guess, correctWord, currWord, missed, wrongCount, head, arms, body, leg1, leg2, remWord);
            currWord = outArray[0];
            missed = outArray[1];
            wrongCount = Integer.parseInt(outArray[2]);
            head = outArray[3];
            arms = outArray[4];
            body = outArray[5];
            leg1 = outArray[6];
            leg2 = outArray[7];
            remWord = outArray[8];
        }

        // win/lose statement
        if (currWord.equals(correctWord)) System.out.println("Nice work! You guessed correctly so no one was hanged!");
        else System.out.println("You've just let a man die by guessing incorrectly too many times. Shame on you.\nThe correct word was " + correctWord + ".");
    }
}