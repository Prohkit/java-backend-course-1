package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {
    static Arguments[] source() {
        return new Arguments[] {
            Arguments.of(2, "II"),
            Arguments.of(12, "XII"),
            Arguments.of(16, "XVI"),
        };
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("The method convert arabic number to roman number")
    void convertArabicNumberToRomanNumber(int arabicNumber, String expectedRomanNumber) {
        // when
        String romanNumber = Task4.convertToRoman(arabicNumber);

        // then
        assertThat(romanNumber)
            .isEqualTo(expectedRomanNumber);
    }
}
