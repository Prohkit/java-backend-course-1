package edu.project4.transformations;

import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("MagicNumber")
public class AffineTransformation {
    private static final int COEFFICIENT_FROM = -1;
    private static final int COEFFICIENT_TO = 1;
    private double a;
    private double b;
    private double c;
    private double d;
    private double e;
    private double f;
    private final int red;
    private final int green;
    private final int blue;

    public AffineTransformation() {
        this.red = ThreadLocalRandom.current().nextInt(0, 256);
        this.green = ThreadLocalRandom.current().nextInt(0, 256);
        this.blue = ThreadLocalRandom.current().nextInt(0, 256);
        this.c = ThreadLocalRandom.current().nextInt(-10, 10);
        this.f = ThreadLocalRandom.current().nextInt(-10, 10);
        generateCoefficients();
    }

    private void generateCoefficients() {
        while (true) {
            double randomA = ThreadLocalRandom.current().nextDouble(COEFFICIENT_FROM, COEFFICIENT_TO);
            double randomB = ThreadLocalRandom.current().nextDouble(COEFFICIENT_FROM, COEFFICIENT_TO);
            double randomD = ThreadLocalRandom.current().nextDouble(COEFFICIENT_FROM, COEFFICIENT_TO);
            double randomE = ThreadLocalRandom.current().nextDouble(COEFFICIENT_FROM, COEFFICIENT_TO);
            if (areCoefficientsCorrect(randomA, randomB, randomD, randomE)) {
                this.a = randomA;
                this.b = randomB;
                this.d = randomD;
                this.e = randomE;
                break;
            }
        }
    }

    private boolean areCoefficientsCorrect(double a, double b, double d, double e) {
        return a * a + d * d < 1
            && b * b + e * e < 1
            && a * a + b * b + d * d + e * e < 1 + (a * e - b * d) * (a * e - b * d);
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public double getE() {
        return e;
    }

    public double getF() {
        return f;
    }
}
