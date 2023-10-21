package edu.project1;

import org.jetbrains.annotations.NotNull;

public record FailedGuess(Session session, char guess) implements GuessResult {
    public FailedGuess {
        session.setAttempts(session.getAttempts() + 1);
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
        return "Missed, mistake " + session.getAttempts() + " out of " + session.getMaxAttempts() + ".";
    }
}
