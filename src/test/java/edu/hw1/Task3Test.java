package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task3Test {

    @Test
    @DisplayName("Может ли первый массив быть вложен во второй")
    void canNestedFirstArrayToSecond() {
        // given
        int[][] firstArrays = {{1, 2, 3, 4}, {3, 1}, {9, 9, 8}, {1, 2, 3, 4}};
        int[][] secondArrays = {{0, 6}, {4, 0}, {8, 9}, {2, 3}};
        boolean[] results = new boolean[4];
        boolean[] expected = {true, true, false, false};

        // when
        for (int i = 0; i < firstArrays.length; i++) {
            results[i] = Task3.isNestable(firstArrays[i], secondArrays[i]);
        }

        // then
        assertThat(results)
            .isEqualTo(expected);
    }
}
