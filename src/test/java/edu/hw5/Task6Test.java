package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {
    static Arguments[] source() {
        return new Arguments[] {
            Arguments.of(
                "abc", "achfdbaabgabcaabg", true
            ),
            Arguments.of(
                "abc", "achfdbaabgabdddddcaabg", false
            )
        };
    }

    @ParameterizedTest
    @MethodSource("source")
    @DisplayName("A string is a subsequence of another string.")
    void isSubstring(String substring, String string, boolean isSubstring) {
        // when
        boolean result = Task6.isSubstring(substring, string);

        // then
        assertThat(result)
            .isEqualTo(isSubstring);
    }
}
