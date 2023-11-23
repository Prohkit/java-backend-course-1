package edu.hw6.task4;

import edu.hw6.Task4;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    private static final Path PATH = Paths.get("./src/test/java/edu/hw6/task4/file.txt");

    @Test
    void task4() throws IOException {
        Task4 task4 = new Task4();
        task4.writeFile(PATH);
        String expected = "Programming is learned by writing programs. ― Brian Kernighan";
        String result = Files.readString(PATH);

        assertThat(result)
            .isEqualTo(expected);

        Files.deleteIfExists(PATH);

    }
}
