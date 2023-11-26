package edu.hw7;

import edu.hw7.task4.MultiThreadPiCalc;
import edu.hw7.task4.PiCalc;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task4Test {
    @Test
    void multiThreadPiCalcTest() {
        // given
        MultiThreadPiCalc piCalc = new MultiThreadPiCalc();
        double pi = 3.1415;
        int iterations = 100_000_000;
        double expectedError = 0.1;

        // when
        double calculatedPI = piCalc.multiThreadedCalculatePi(iterations);
        double error = (Math.abs(pi - calculatedPI) / pi) * 100;

        // then
        assertTrue(error <= expectedError);
    }

    @Test
    void piCalcTest() {
        // given
        PiCalc piCalc = new PiCalc();
        double pi = 3.1415;
        int iterations = 1_000_000;
        double expectedError = 0.1;

        // when
        double calculatedPI = piCalc.calculatePi(iterations);
        double error = (Math.abs(pi - calculatedPI) / pi) * 100;

        // then
        assertTrue(error <= expectedError);
    }
}
