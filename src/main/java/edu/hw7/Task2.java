package edu.hw7;

import java.util.List;
import java.util.Optional;

public class Task2 {
    public Integer calculateFactorial(List<Integer> number) {
        Optional<Integer> result = number.parallelStream().reduce((x, y) -> x * y);
        return result.orElse(null);
    }
}
