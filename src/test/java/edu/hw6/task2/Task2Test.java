package edu.hw6.task2;

import edu.hw6.Task2;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    private static final Path FILE_TO_COPY = Paths.get("./src/test/java/edu/hw6/task2/Tinkoff Bank Biggest Secret.txt");

    @Test
    void task2() {
        Task2 task2 = new Task2();

        for (int i = 1; i <= 5; i++) {
            task2.cloneFile(FILE_TO_COPY);
            Path copy;
            if (i == 1) {
                copy = Paths.get("./src/test/java/edu/hw6/task2/Tinkoff Bank Biggest Secret - копия.txt");
            } else {
                copy = Paths.get("./src/test/java/edu/hw6/task2/Tinkoff Bank Biggest Secret - копия (" + i + ").txt");
            }
            assertThat(Files.exists(copy))
                .isTrue();
        }
    }

    @AfterAll
    static void destroy() throws IOException {
        for (int i = 1; i <= 5; i++) {
            Path toDelete;
            if (i == 1) {
                toDelete = Paths.get("./src/test/java/edu/hw6/task2/Tinkoff Bank Biggest Secret - копия.txt");
            } else {
                toDelete = Paths.get("./src/test/java/edu/hw6/task2/Tinkoff Bank Biggest Secret - копия (" + i + ").txt");
            }
            Files.deleteIfExists(toDelete);
        }
    }
}
