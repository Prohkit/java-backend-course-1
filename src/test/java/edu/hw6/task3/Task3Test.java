package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static edu.hw6.task3.Filters.globMatches;
import static edu.hw6.task3.Filters.largerThan;
import static edu.hw6.task3.Filters.readable;
import static edu.hw6.task3.Filters.regexContains;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    private static final Path PATH = Paths.get("src/test/java/edu/hw6/task3/files");

    @Test
    void task3() throws IOException {
        String correctFileName = "correctFile.glob";
        DirectoryStream.Filter<Path> filter = regexContains("\\D+")
            .and(globMatches("*.glob"))
            .and(readable)
            .and(largerThan(5000));
        List<Path> files = new ArrayList<>();

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(PATH, filter)) {
            entries.forEach(files::add);
        }

        assertThat(files.size())
            .isEqualTo(1);

        assertThat(files.get(files.size() - 1).getFileName().toString())
            .isEqualTo(correctFileName);
    }
}
