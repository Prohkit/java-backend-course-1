package edu.hw10.task2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CacheProxyTest {
    @Test
    void test() {
        FibCalculator fib = new FibonacciCalculator();
        FibCalculator proxy = CacheProxy.create(fib, FibCalculator.class);

        proxy.fib(5);
        Path cacheDirPath = Path.of("src/main/java/edu/hw10/task2/cacheStorage/");

        try (var walk = Files.walk(cacheDirPath)) {
            long filesCount = walk
                .filter(p -> !p.toFile().isDirectory())
                .count();
            assertEquals(1, filesCount);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @BeforeAll static void createCacheStorageIfNotExist() {
        File cacheDir = new File("src/main/java/edu/hw10/task2/cacheStorage/");
        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }
    }

    @AfterAll static void removeCacheFiles() {
        File cacheDir = new File("src/main/java/edu/hw10/task2/cacheStorage/");
        for (File file : cacheDir.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
    }
}
