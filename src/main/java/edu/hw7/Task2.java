package edu.hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Task2 {
    public Integer calculateFactorial(int number) {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            list.add(i);
        }
        Optional<Integer> result = list.parallelStream().reduce((x, y) -> x * y);
        return result.orElse(null);
    }
}
