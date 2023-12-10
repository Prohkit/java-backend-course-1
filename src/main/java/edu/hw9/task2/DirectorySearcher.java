package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class DirectorySearcher extends RecursiveTask<List<Path>> {
    private static final int EXPECTED_FILES_COUNT = 1_000;
    private final Path dir;

    public DirectorySearcher(Path dir) {
        this.dir = dir;
    }

    @Override
    protected List<Path> compute() {
        List<Path> result = new ArrayList<>();
        ConcurrentLinkedQueue<ForkJoinTask<List<Path>>> tasks = new ConcurrentLinkedQueue<>();
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(dir)) {
            AtomicInteger filesCount = new AtomicInteger();
            paths.forEach(path -> {
                if (Files.isDirectory(path)) {
                    tasks.add(new DirectorySearcher(path).fork());
                } else {
                    filesCount.getAndIncrement();
                }
            });
            if (filesCount.get() > EXPECTED_FILES_COUNT) {
                result.add(dir);
            }
        } catch (IOException e) {
        }
        tasks.forEach(task -> result.addAll(task.join()));
        return result;
    }
}
