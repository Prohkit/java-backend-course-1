package edu.project1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("RegexpSinglelineJava")
public class Session {
    private final String answer;
    private final char[] userAnswer;
    private final int maxAttempts;
    private int attempts;
    private final Set<Character> usedLetters;

    public Session(String answer, int maxAttempts) {
        this.answer = answer;
        this.userAnswer = "*".repeat(answer.length()).toCharArray();
        this.maxAttempts = maxAttempts;
        this.attempts = 0;
        this.usedLetters = new HashSet<>();
    }

    public boolean hasLetterAlreadyBeenUsed(Character letter) {
        return usedLetters.contains(letter);
    }

    public String getAnswer() {
        return answer;
    }

    public char[] getUserAnswer() {
        return userAnswer;
    }

    public void updateUserAnswer(char guess) {
        for (int i = 0; i < this.answer.length(); i++) {
            if (this.answer.charAt(i) == guess) {
                this.userAnswer[i] = guess;
            }
        }
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Set<Character> getUsedLetters() {
        return usedLetters;
    }

    public void addUsedLetter(Character usedLetter) {
        this.usedLetters.add(usedLetter);
    }

    public void printUsedLetters() {
        System.out.println("Letter already been used. Used letters:");
        Iterator<Character> iterator = this.getUsedLetters().stream().iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next());
            if (iterator.hasNext()) {
                System.out.print(", ");
            } else {
                System.out.println();
            }
        }
    }

    @NotNull GuessResult guess(char guess) {
        if (answer.contains(String.valueOf(guess))) {
            return new SuccessfulGuess(this, guess);
        }
        return new FailedGuess(this, guess);
    }

    @NotNull GuessResult giveUp() {
        return new Defeat(this);
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return maxAttempts == session.maxAttempts && attempts == session.attempts
            && Objects.equals(answer, session.answer) && Arrays.equals(userAnswer, session.userAnswer)
            && Objects.equals(usedLetters, session.usedLetters);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(answer, maxAttempts, attempts, usedLetters);
        result = 31 * result + Arrays.hashCode(userAnswer);
        return result;
    }
}
