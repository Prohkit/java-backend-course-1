package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {

    @Test
    @DisplayName("Возвращает количество цифр в числе")
    void methodReturnsNumberOfDigits() {
        // given
        int number = 123;
        int negativeNumber = -123;
        int expectedValue = 3;

        // when
        int numberOfDigits = Task2.countDigits(number);
        int numberOfDigitsInNegativeNumber = Task2.countDigits(negativeNumber);

        // then
        assertThat(numberOfDigits)
            .isPositive()
            .isEqualTo(expectedValue);

        assertThat(numberOfDigitsInNegativeNumber)
            .isPositive()
            .isEqualTo(expectedValue);
    }
}
