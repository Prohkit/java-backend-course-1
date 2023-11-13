package edu.hw5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task8Test {

    @Test
    void isOddLength() {
        // given
        String oddLength = "101";
        String evenLength = "10";

        // then
        assertTrue(Task8.isOddLength(oddLength));
        assertFalse(Task8.isOddLength(evenLength));
    }

    @Test
    void isBeginFromZeroAndOddLengthOrIsBeginOfOneAndEvenLength() {
        // given
        String beginFromZeroAndOddLength = "001";
        String beginOfOneAndEvenLength = "10";
        String beginFromZeroAndEvenLength = "01";
        String beginOfOneAndOddLength = "101";

        // then
        assertTrue(Task8.isBeginFromZeroAndOddLengthOrIsBeginOfOneAndEvenLength(beginFromZeroAndOddLength));
        assertTrue(Task8.isBeginFromZeroAndOddLengthOrIsBeginOfOneAndEvenLength(beginOfOneAndEvenLength));
        assertFalse(Task8.isBeginFromZeroAndOddLengthOrIsBeginOfOneAndEvenLength(beginFromZeroAndEvenLength));
        assertFalse(Task8.isBeginFromZeroAndOddLengthOrIsBeginOfOneAndEvenLength(beginOfOneAndOddLength));
    }

    @Test
    void isNotEqualElevenOrOneHundredEleven() {
        // given
        String eleven = "11";
        String OneHundredEleven = "111";
        String correctString = "1111";

        // then
        assertFalse(Task8.isNotEqualElevenOrOneHundredEleven(eleven));
        assertFalse(Task8.isNotEqualElevenOrOneHundredEleven(OneHundredEleven));
        assertTrue(Task8.isNotEqualElevenOrOneHundredEleven(correctString));
    }

    @Test
    void isEveryOddCharEqualToOne() {
        // given
        String everyOddCharEqualToOne = "10101";
        String notEveryOddCharEqualToOne = "10001";

        // then
        assertTrue(Task8.isEveryOddCharEqualToOne(everyOddCharEqualToOne));
        assertFalse(Task8.isEveryOddCharEqualToOne(notEveryOddCharEqualToOne));
    }

    @Test
    void doesNotContainConsecutiveOnes() {
        // given
        String stringWithoutConsecutiveOnes = "100101";
        String stringWithConsecutiveOnes = "101101";

        // then
        assertTrue(Task8.doesNotContainConsecutiveOnes(stringWithoutConsecutiveOnes));
        assertFalse(Task8.doesNotContainConsecutiveOnes(stringWithConsecutiveOnes));
    }
}
