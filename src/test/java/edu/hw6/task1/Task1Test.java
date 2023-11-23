package edu.hw6.task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {

    private static DiskMap diskMap;
    private static final Path PATH = Paths.get("src/test/java/edu/hw6/task1/diskMap.txt");

    @BeforeAll
    static void init() {
        diskMap = new DiskMap(PATH.toString());
    }

    @BeforeEach
    void clear() {
        diskMap.clear();
    }

    @Test
    void saveToDiskMap() throws IOException {
        // given
        String expected = "key:value" + System.lineSeparator();

        // when
        diskMap.put("key", "value");
        diskMap.save();

        // then
        assertThat(Files.readString(PATH))
            .isEqualTo(expected);
    }

    @Test
    void clearDiskMap() throws IOException {
        diskMap.put("key0", "value0");
        diskMap.put("key1", "value1");
        diskMap.save();
        int requiredNumberOfRecordsBeforeClear = 2;
        int requiredNumberOfRecordsAfterClear = 0;

        assertThat(diskMap.size())
            .isEqualTo(requiredNumberOfRecordsBeforeClear);

        diskMap.clear();

        assertThat(diskMap.size())
            .isEqualTo(requiredNumberOfRecordsAfterClear);
    }

    @Test
    void loadFromDiskMap() {
        diskMap.put("anotherKey", "anotherValue");
        diskMap.save();

        diskMap.clear();
        assertThat(diskMap.isEmpty())
            .isTrue();

        diskMap.load();

        assertThat(diskMap.isEmpty())
            .isFalse();
    }

    @AfterAll
    static void destroy() throws IOException {
        Files.delete(PATH);
    }
}
