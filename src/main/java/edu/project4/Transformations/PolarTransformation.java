package edu.project4.Transformations;

public class PolarTransformation {
    public double getNewX(double x, double y) {
        return Math.atan(y / x) / Math.PI;
    }

    public double getNewY(double x,double y) {
        return Math.sqrt(x * x + y * y) - 1;
    }
}
