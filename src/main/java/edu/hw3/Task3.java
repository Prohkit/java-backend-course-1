package edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Task3 {
    private Task3() {
    }

    public static <T> Map<T, Integer> freqDict(List<T> inputList) {
        Map<T, Integer> frequencyDictionary = new HashMap<>();
        for (T t : inputList) {
            if (frequencyDictionary.containsKey(t)) {
                frequencyDictionary.put(t, frequencyDictionary.get(t) + 1);
            } else {
                frequencyDictionary.put(t, 1);
            }
        }
        return frequencyDictionary;
    }
}
