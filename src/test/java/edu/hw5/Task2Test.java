package edu.hw5;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    static Arguments[] source() {
        return new Arguments[] {
            Arguments.of(
                1925,
                List.of(
                    LocalDate.of(1925, 2, 13),
                    LocalDate.of(1925, 3, 13),
                    LocalDate.of(1925, 11, 13)
                )
            ),
            Arguments.of(
                2024,
                List.of(
                    LocalDate.of(2024, 9, 13),
                    LocalDate.of(2024, 12, 13)
                )
            ),
        };
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("returns all Friday the thirteenth of the year")
    void getAllFridays(int year, List<LocalDate> fridaysTheThirteen) {
        // when
        List<LocalDate> encryptedOffer = Task2.getAllFridays(year);

        // then
        assertThat(encryptedOffer)
            .isEqualTo(fridaysTheThirteen);
    }

    @Test
    void getNextFridayTheThirteen() {
        // given
        LocalDate date = LocalDate.of(2023, 11, 13);
        LocalDate exceptedDate = LocalDate.of(2024, 9, 13);

        // when
        LocalDate nextFridayTheThirteen = Task2.getNextFridayTheThirteen(date);

        // then
        assertThat(nextFridayTheThirteen)
            .isEqualTo(exceptedDate);
    }
}
