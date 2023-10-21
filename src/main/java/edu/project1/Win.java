package edu.project1;

import org.jetbrains.annotations.NotNull;

public record Win(Session session) implements GuessResult {
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
        return "You won!";
    }
}
