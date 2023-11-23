package edu.hw6.task2;

import edu.hw6.Task2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


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
        int expectedCount = 4;
        try (Stream<Path> files = Files.list(Paths.get("./src/test/java/edu/hw6/task2").toAbsolutePath())) {
            long count = files.count();
            assertThat(count)
                .isEqualTo(expectedCount);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void destroy() throws IOException {
        Files.deleteIfExists(COPY);
        Files.deleteIfExists(COPY_SECOND);
    }
}
