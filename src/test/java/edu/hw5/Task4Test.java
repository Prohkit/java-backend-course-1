package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    static Arguments[] source() {
        return new Arguments[] {
            Arguments.of(
                "asd!", true
            ),
            Arguments.of(
                "as!!d", false
            ),
            Arguments.of(
                "!asd$", false
            )
        };
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("Is the password valid")
    void isValidPassword(String password, boolean isValidPassword) {
        // when
        boolean result = Task4.isValidPassword(password);

        // then
        assertThat(result)
            .isEqualTo(isValidPassword);
    }
}
