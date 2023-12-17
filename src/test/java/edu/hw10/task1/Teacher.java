package edu.hw10.task1;

import edu.hw10.task1.annotation.Max;
import edu.hw10.task1.annotation.Min;
import edu.hw10.task1.annotation.NotNull;

@SuppressWarnings("MagicNumber")
public record Teacher(String name, @NotNull String lastName, long id, @Min(100) int min, @Max(500) int max) {
    public static Teacher create(String name, String lastName) {
        return new Teacher(name, lastName, 1000L, 200, 499);
    }
}
