package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {

    @Test
    @DisplayName("Получение числа Капрекара")
    void getKaprekarsNumber() {
        // given
        int[] inputNumbers = {3524, 6621, 6554, 1234};
        int[] stepsCount = new int[inputNumbers.length];
        int[] expected = {3, 5, 4, 3};

        // when
        for (int i = 0; i < inputNumbers.length; i++) {
            stepsCount[i] = Task6.countK(inputNumbers[i]);
        }

        // then
        assertThat(stepsCount)
            .isEqualTo(expected);
    }
}
