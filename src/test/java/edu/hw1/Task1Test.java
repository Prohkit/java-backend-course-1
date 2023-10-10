package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task1Test {

    @Test
    @DisplayName("Возвращает общую длину видео в секундах")
    void methodReturnsVideoLength() {
        // given
        String[] videoLength = {"12:53", "123", "12:123", ":53", "1a3:53", "12:61"};
        int[] videoLengthInSeconds = new int[videoLength.length];
        int[] expected = {773, -1, -1, -1, -1, -1};

        // when
        for (int i = 0; i < videoLength.length; i++) {
            videoLengthInSeconds[i] = Task1.minutesToSeconds(videoLength[i]);
        }

        // then
        assertThat(videoLengthInSeconds)
            .isNotNull()
            .isEqualTo(expected);
    }
}
