package edu.hw7;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {

    @Test
    void task1() {
        // given
        Task1 task1 = new Task1();
        int iterationForThread = 500;
        int expected = 1000;

        // when
        int result = task1.atomicCounter(iterationForThread);

        // then
        assertThat(result)
            .isEqualTo(expected);
    }
}
