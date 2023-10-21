package edu.project1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Project1Test {
    private ByteArrayOutputStream runWithAttachedInputOutputStreams(
        ConsoleHangman consoleHangman,
        ByteArrayInputStream in
    ) throws IOException {
        InputStream sysInBackup = System.in;
        PrintStream sysOutBackup = System.out;
        System.setIn(in);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outputStream);
        System.setOut(out);

        consoleHangman.run();
        System.setIn(sysInBackup);
        System.setOut(sysOutBackup);
        return outputStream;
    }

    @Test
    @DisplayName("После превышения заданного количества попыток игра возвращает поражение")
    void defeatIfNumberOfAttemptsExceeded() throws IOException {
        // given
        ConsoleHangman consoleHangman = new ConsoleHangman(1);
        String exceptedEnd = "You lost!" + System.lineSeparator();

        // when
        ByteArrayInputStream in = new ByteArrayInputStream("q".getBytes());
        ByteArrayOutputStream outputStream = runWithAttachedInputOutputStreams(consoleHangman, in);

        // then
        assertThat(outputStream.toString().endsWith(exceptedEnd))
            .isTrue();
    }

    @Test
    @DisplayName("Cостояние игры корректно изменяется (при угадывании/не угадывании)")
    void theGameStateChangesCorrectly() throws IOException {
        // given
        Session session = new Session("aaaag", 2);
        String exceptedUserAnswer = "aaaa*";
        int exceptedMistakesCount = 1;

        // when
        session.guess('a');
        session.guess('i');

        // then
        assertThat(String.valueOf(session.getUserAnswer()))
            .isEqualTo(exceptedUserAnswer);

        assertThat(session.getAttempts())
            .isEqualTo(exceptedMistakesCount);
    }

    @Test
    @DisplayName("Ввод строки длиной больше чем 1 (опечатка) приводит к повторному вводу, без изменения состояния")
    void illegalInput() throws IOException {
        // given
        ConsoleHangman consoleHangman = new ConsoleHangman(1);
        String exceptedString = "Guess a letter:"
            + System.lineSeparator()
            + "you need to enter one letter."
            + System.lineSeparator()
            + "Guess a letter:";

        // when
        Session sessionBefore = consoleHangman.getSession();
        ByteArrayInputStream in = new ByteArrayInputStream("qa".getBytes());
        ByteArrayOutputStream outputStream = runWithAttachedInputOutputStreams(consoleHangman, in);

        // then
        assertThat(outputStream.toString().contains(exceptedString))
            .isTrue();

        assertThat(consoleHangman.getSession())
            .isEqualTo(sessionBefore);
    }
}
