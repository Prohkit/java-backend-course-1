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
import java.util.function.Predicate;

public class FileSearcher extends RecursiveTask<List<Path>> {
    private final Path dir;
    private final List<Predicate<Path>> predicates;

    public FileSearcher(Path dir, List<Predicate<Path>> predicates) {
        this.dir = dir;
        this.predicates = predicates;
    }

    @Override
    protected List<Path> compute() {
        List<Path> result = new ArrayList<>();
        ConcurrentLinkedQueue<ForkJoinTask<List<Path>>> tasks = new ConcurrentLinkedQueue<>();
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(dir)) {
            paths.forEach(path -> {
                if (Files.isDirectory(path)) {
                    tasks.add(new FileSearcher(path, predicates).fork());
                } else {
                    if (isCorrectFile(path)) {
                        result.add(path);
                    }
                }
            });
        } catch (IOException e) {
        }
        tasks.forEach(task -> result.addAll(task.join()));
        return result;
    }

    private boolean isCorrectFile(Path path) {
        return predicates.stream().allMatch(it -> it.test(path));
    }
}
