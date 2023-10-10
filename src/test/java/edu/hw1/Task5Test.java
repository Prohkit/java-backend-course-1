package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task5Test {

    @Test
    @DisplayName("Поиск палиндрома")
    void palindromeSearch() {
        // given
        int[] inputNumbers = {11211230, 13001120, 23336014, 11, 89, 99998898};
        boolean[] isPalindrome = new boolean[inputNumbers.length];
        boolean[] expected = {true, true, true, true, false, false};

        // when
        for (int i = 0; i < inputNumbers.length; i++) {
            isPalindrome[i] = Task5.isPalindromeDescendant(inputNumbers[i]);
        }

        // then
        assertThat(isPalindrome)
            .isEqualTo(expected);
    }
}
