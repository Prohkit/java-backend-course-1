package edu.hw6.task2;

import edu.hw6.Task2;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task2Test {
    private static final Path FILE_TO_COPY = Paths.get("./src/test/java/edu/hw6/task2/Tinkoff Bank Biggest Secret.txt");
    private static final Path COPY = Paths.get("./src/test/java/edu/hw6/task2/Tinkoff Bank Biggest Secret - копия.txt");
    private static final Path COPY_SECOND =
        Paths.get("./src/test/java/edu/hw6/task2/Tinkoff Bank Biggest Secret - копия (2).txt");

    @Test
    void task2() {
        Task2 task2 = new Task2();
        task2.cloneFile(FILE_TO_COPY);
        assertTrue(Files.exists(COPY));

        task2.cloneFile(FILE_TO_COPY);
        assertTrue(Files.exists(COPY_SECOND));
    }

    @AfterAll
    static void destroy() throws IOException {
        Files.deleteIfExists(COPY);
        Files.deleteIfExists(COPY_SECOND);
    }
}
