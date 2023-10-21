package edu.project1;

import org.jetbrains.annotations.NotNull;

public record SuccessfulGuess(Session session, char guess) implements GuessResult {
    public SuccessfulGuess {
        session.updateUserAnswer(guess);
        session.addUsedLetter(guess);
    }

    @Override
    public char[] state() {
        return session.getUserAnswer();
    }

    @Override
    public int attempt() {
        return session.getAttempts();
    }

    @Override
    public int maxAttempts() {
        return session.getMaxAttempts();
    }

    @Override
    public @NotNull String message() {
        return "Hit!";
    }
}
