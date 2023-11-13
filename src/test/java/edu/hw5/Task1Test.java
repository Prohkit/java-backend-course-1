package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {
    static Arguments[] source() {
        return new Arguments[] {
            Arguments.of(
                List.of(
                    "2022-03-12, 20:20 - 2022-03-12, 23:50",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20"
                ),
                "3ч 40м"
            ),
            Arguments.of(
                List.of(
                    "2023-04-19, 18:30 - 2023-04-19, 21:00",
                    "2023-05-30, 22:00 - 2023-05-31, 06:00"
                ),
                "5ч 15м"
            )
        };
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("Returns the average time spent in the computer club")
    void getTimePeriodByString(List<String> times, String expected) {
        // when
        String encryptedOffer = Task1.getTimePeriodByString(times);

        // then
        assertThat(encryptedOffer)
            .isEqualTo(expected);
    }
}
