package edu.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import static edu.project1.Dictionary.randomWord;

@SuppressWarnings("RegexpSinglelineJava")
public class ConsoleHangman {
    private final Session session;
    private GuessResult guessResult;

    public ConsoleHangman(int maxAttempts) {
        this.session = new Session(randomWord(), maxAttempts);
        this.guessResult = null;
    }

    public Session getSession() {
        return session;
    }

    public void run() throws IOException {
        do {
            String input = getInput();
            // if ctrl+D
            if (input == null) {
                guessResult = session.giveUp();
            } else if (input.length() != 1) {
                System.out.println("you need to enter one letter.");
            } else if (!session.hasLetterAlreadyBeenUsed(input.charAt(0))) {
                guessResult = tryGuess(session, input.charAt(0));
            } else {
                session.printUsedLetters();
            }
        } while (gameContinues(guessResult));
    }

    private String getInput() throws IOException {
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        System.out.println("Guess a letter:");
        return systemIn.readLine();
    }

    private GuessResult tryGuess(Session session, char input) {
        guessResult = session.guess(input);
        printMessage(guessResult);
        if (!String.valueOf(guessResult.state()).contains("*")) {
            guessResult = new Win(session);
        } else if (guessResult.maxAttempts() == guessResult.attempt()) {
            guessResult = new Defeat(session);
        }
        printState(guessResult);
        return guessResult;
    }

    private boolean gameContinues(GuessResult guessResult) {
        if ((guessResult instanceof Win) || (guessResult instanceof Defeat)) {
            System.out.println(guessResult.message());
            return false;
        }
        return true;
    }

    private void printMessage(GuessResult guessResult) {
        System.out.println(guessResult.message());
    }

    private void printState(GuessResult guess) {
        System.out.println(System.lineSeparator()
            + "The word: "
            + String.valueOf(guess.state())
            + System.lineSeparator());
    }
}
