package edu.hw7.task4;

@SuppressWarnings("MagicNumber")
public class PiCalc {
    public double calculatePi(int n) {
        double inCircleCount = 0;

        for (int i = 0; i < n; i++) {
            double x = Math.random();
            double y = Math.random();
            if (x * x + y * y <= 1) {
                inCircleCount++;
            }
        }
        return 4 * (inCircleCount / n);
    }
}
