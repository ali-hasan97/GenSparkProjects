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
    public static void printMan(String missed, String currWord) {
        System.out.println(HangmanArt.hangmanArt(missed.length()));
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
    public static String[] checkGuess(String guess, String correctWord, String currWord, String missed, int wrongCount, String remWord) {
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
                String[] strArray = new String[] {currWord, missed, String.valueOf(wrongCount), remWord};
                return strArray;
            };
        }

        // if currWord is different from prevWord, it means the guess was correct, so return here
        if (!currWord.equals(prevWord)) {
            printMan(missed, currWord);
            String[] strArray = new String[] {currWord, missed, String.valueOf(wrongCount), remWord};
            return strArray;
        }

        // if guess is not update wrongCount and missed
        wrongCount++;
        missed += guess;
        printMan(missed, currWord);
        return new String[] {currWord, missed, String.valueOf(wrongCount), remWord};
    }

    public static void findHighScore() {
        try {
            List<String> scores = Files.readAllLines(Paths.get("./src/Hangman/Record.txt"), StandardCharsets.UTF_8);
            ArrayList<Object> highestScore = new ArrayList<>();
            String name = scores.get(scores.size() - 1).split(" - ")[0];
            int score = Integer.parseInt(scores.get(scores.size() - 1).split(" - ")[1]);
            highestScore.add(name);
            highestScore.add(score);
            scores.stream()
                    .map(eachScore -> eachScore.split(" - "))
                    .forEach(parts -> {
                        if (((int) highestScore.get(1)) < Integer.parseInt(parts[1])) {
                            highestScore.set(0, parts[0]);
                            highestScore.set(1, Integer.parseInt(parts[1]));
                        }
                    });
            if ((int) highestScore.get(1) == score) {
                System.out.println("Congrats! You hold the highest score with " + score + " games won in a row! Well played.");
            } else {
                System.out.println("Unfortunately your score of " + score + " is not the highest score. The current highest score is held by " + highestScore.get(0) + " with " + highestScore.get(1) + " games won in a row!");
            }
        } catch (Exception e) {
            System.out.println("Couldn't find high score");
        }
    }

    public static void main(String[] args) {

        String guess = " ";
        String missed = "";
        String currWord = "";
        int currScore = 0;
        int highScore = 0;
        String numbers = "0123456789";
        int wrongCount = 0;
        boolean rematch = true;

        System.out.println("H A N G M A N");
        System.out.println("You have 5 guesses before the man is hanged. Guess each letter of the word, one letter at a time.");
        printMan(missed, currWord);
        String correctWord = generateWord();
        String remWord = correctWord;
        currWord = correctWord.codePoints().mapToObj(c -> "_").collect(Collectors.joining());
        System.out.println(currWord);

        // main game loop
        while (wrongCount < 5 && !currWord.equals(correctWord) && rematch) {

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
                    printMan(missed, currWord);
                }
            }
            if (!currWord.equals(correctWord)) guess = takeGuess();

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
            String[] outArray = checkGuess(guess, correctWord, currWord, missed, wrongCount, remWord);
            currWord = outArray[0];
            missed = outArray[1];
            wrongCount = Integer.parseInt(outArray[2]);
            remWord = outArray[3];

            // win/lose statement
            if (currWord.equals(correctWord) || wrongCount >= 5) {
                if (currWord.equals(correctWord)) {
                    System.out.println("Nice work! You guessed correctly so no one was hanged!\nDo you want to keep playing? (y or n)");
                    currScore += 1;
                } else {
                    System.out.println("You've just let a man die by guessing incorrectly too many times. Shame on you.\nThe correct word was " + correctWord + ".\nDo you want to play again? (y or n)");
                    highScore = Math.max(highScore, currScore);
                    currScore = 0;
                }
                try {
                    Scanner sc = new Scanner(System.in);
                    String rematchInput = sc.nextLine();
                    if (rematchInput.equalsIgnoreCase("y")) {
                        System.out.println("You have 5 guesses before the man is hanged. Guess each letter of the word, one letter at a time.");
                        guess = " ";
                        missed = "";
                        wrongCount = 0;
                        correctWord = generateWord();
                        remWord = correctWord;
                        currWord = correctWord.codePoints().mapToObj(c -> "_").collect(Collectors.joining());
                        printMan(missed, currWord);
                    } else if (rematchInput.equalsIgnoreCase("n")) {
                        rematch = false;
                        try {
                            FileWriter myWriter = new FileWriter("./src/Hangman/Record.txt", true);
                            BufferedWriter bw = new BufferedWriter(myWriter);
                            System.out.println("Please enter your name so your high score can be recorded.");
                            bw.write(sc.nextLine() + " - " + Math.max(highScore, currScore));
                            bw.newLine();
                            bw.close();
                            sc.close();
                            findHighScore();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        System.out.println("Thanks for playing!");
                    } else {
                        rematch = false;
                        System.out.println("Invalid input. Exiting game.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Exiting game.");
                }
            }
        }
    }
}