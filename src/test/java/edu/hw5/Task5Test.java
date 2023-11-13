package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {
    static Arguments[] source() {
        return new Arguments[] {
            Arguments.of(
                "А123ВЕ777", true
            ),
            Arguments.of(
                "А123ВЕ77", true
            ),
            Arguments.of(
                "123АВЕ777", false
            ),
            Arguments.of(
                "А123ВГ77", false
            ),
            Arguments.of(
                "А123ВЕ7777", false
            )
        };
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("Is the car number correct?")
    void isValidRussianLicensePlates(String licensePlate, boolean isValidLicensePlate) {
        // when
        boolean result = Task5.isValidRussianLicensePlates(licensePlate);

        // then
        assertThat(result)
            .isEqualTo(isValidLicensePlate);
    }
}
