package edu.hw5;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task7Test {
    @Test
    void hasThreeSymbolAndEndsWithZero() {
        // given
        String fourSymbols = "1010";
        String correctInput = "100";

        // then
        assertTrue(Task7.hasThreeSymbolAndEndsWithZero(correctInput));
        assertFalse(Task7.hasThreeSymbolAndEndsWithZero(fourSymbols));
    }

    @Test
    void startsAndEndsWithTheSameCharacter() {
        // given
        String startsWithZero = "010";
        String startsWithOne = "101";
        String twoSymbols = "11";
        String incorrectInput = "100";

        // then
        assertTrue(Task7.startsAndEndsWithTheSameCharacter(startsWithZero));
        assertTrue(Task7.startsAndEndsWithTheSameCharacter(startsWithOne));
        assertTrue(Task7.startsAndEndsWithTheSameCharacter(twoSymbols));
        assertFalse(Task7.startsAndEndsWithTheSameCharacter(incorrectInput));
    }

    @Test
    void fromOneToThreeLength() {
        // given
        String zeroLength = "";
        String oneLength = "1";
        String twoLength = "10";
        String threeLength = "100";
        String fourLength = "1001";

        // then
        assertFalse(Task7.fromOneToThreeLength(zeroLength));
        assertTrue(Task7.fromOneToThreeLength(oneLength));
        assertTrue(Task7.fromOneToThreeLength(twoLength));
        assertTrue(Task7.fromOneToThreeLength(threeLength));
        assertFalse(Task7.fromOneToThreeLength(fourLength));
    }
}
