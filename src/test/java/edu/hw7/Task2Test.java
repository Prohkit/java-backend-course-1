package edu.hw7;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {

    @Test
    void task2() {
        // given
        Task2 task2 = new Task2();
        int numberToFactorial = 5;
        int expected = 120;

        // when
        int result = task2.calculateFactorial(numberToFactorial);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }
}
