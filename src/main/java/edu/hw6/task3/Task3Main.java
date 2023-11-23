package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static edu.hw6.task3.Filters.globMatches;
import static edu.hw6.task3.Filters.regexContains;

public class Task3Main {
    private Task3Main() {
    }

    public static void main(String[] args) {
        DirectoryStream.Filter<Path> filter = regexContains(".*[F].*")
            .and(globMatches("*.txt"));

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(
            Path.of("./src/main/java/edu/hw6/task3"),
            filter
        )) {
            entries.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
