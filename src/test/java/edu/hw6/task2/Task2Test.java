package edu.hw6.task2;

import edu.hw6.Task2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Task2Test {
    private static final Path FILE =
        Path.of("./src/test/java/edu/hw6/task2/Tinkoff Bank Biggest Secret.txt").toAbsolutePath();
    private static final Path COPY =
        Path.of("./src/test/java/edu/hw6/task2/Tinkoff Bank Biggest Secret - copy.txt").toAbsolutePath();
    private static final Path COPY_SECOND = Path.of(
        "./src/test/java/edu/hw6/task2/Tinkoff Bank Biggest Secret - copy (2).txt").toAbsolutePath();

    @BeforeAll
    static void init() {
        Task2 task2 = new Task2();
        task2.cloneFile(FILE);
        task2.cloneFile(FILE);
    }

    @Test
    void task2() {
        Assertions.assertTrue(Files.exists(COPY));
        Assertions.assertTrue(Files.exists(COPY_SECOND));
    }

    @AfterAll
    static void destroy() throws IOException {
        Files.deleteIfExists(COPY);
        Files.deleteIfExists(COPY_SECOND);
    }
}
