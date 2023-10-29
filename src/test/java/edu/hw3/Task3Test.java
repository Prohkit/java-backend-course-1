package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {
    static Arguments[] source() {
        return new Arguments[] {
            Arguments.of(List.of("a", "bb", "a", "bb"), new HashMap<String, Integer>() {{
                put("bb", 2);
                put("a", 2);
            }}),
            Arguments.of(List.of("this", "and", "that", "and"), new HashMap<String, Integer>() {{
                put("that", 1);
                put("and", 2);
                put("this", 1);
            }}),
            Arguments.of(List.of("код", "код", "код", "bug"), new HashMap<String, Integer>() {{
                put("код", 3);
                put("bug", 1);
            }}),
            Arguments.of(List.of(1, 1, 2, 2), new HashMap<Integer, Integer>() {{
                put(1, 2);
                put(2, 2);
            }})
        };
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("The method returns the frequency dictionary")
    <T> void returnsTheFrequencyDictionary(
        List<T> objects,
        Map<T, Integer> ExpectedFrequencyDictionary
    ) {
        // when
        Map<T, Integer> frequencyDictionary = Task3.freqDict(objects);

        // then
        assertThat(frequencyDictionary)
            .isEqualTo(ExpectedFrequencyDictionary);
    }
}
