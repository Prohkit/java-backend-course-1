package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task7Test {

    @Test
    @DisplayName("Циклический битовый сдвиг")
    void cyclicallyShiftBits() {
        // given
        int[] inputNumbers = {8, 9, 16, 17};
        int[] shifts = {1, 2, 1, 2};
        int[] result = new int[inputNumbers.length];
        int[] expected = {4, 6, 1, 6};

        // when
        result[0] = Task7.rotateRight(inputNumbers[0], shifts[0]);
        result[1] = Task7.rotateRight(inputNumbers[1], shifts[1]);

        result[2] = Task7.rotateLeft(inputNumbers[2], shifts[2]);
        result[3] = Task7.rotateLeft(inputNumbers[3], shifts[3]);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }
}
