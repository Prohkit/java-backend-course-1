package edu.project4.Transformations;

import java.util.Random;

public class SinusoidalTransformation {

    public double getNewX(double x) {
        return Math.sin(x);
    }

    public double getNewY(double y) {
        return Math.sin(y);
    }
}
