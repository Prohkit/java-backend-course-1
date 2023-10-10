package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task4Test {

    @Test
    @DisplayName("Исправление строки")
    void stringCorrection() {
        // given
        String[] brokenStrings = {"123456", "hTsii  s aimex dpus rtni.g", "badce"};
        String[] correctStrings = {"214365", "This is a mixed up string.", "abcde"};

        // when
        for (int i = 0; i < brokenStrings.length; i++) {
            correctStrings[i] = Task4.fixString(brokenStrings[i]);
        }

        // then
        assertThat(correctStrings)
            .isEqualTo(correctStrings);
    }
}
