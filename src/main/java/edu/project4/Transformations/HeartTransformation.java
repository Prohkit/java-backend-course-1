package edu.project4.Transformations;

public class HeartTransformation {
    public double getNewX(double x, double y) {
        double sqrt = Math.sqrt(x * x + y * y);
        return sqrt * Math.sin(sqrt * Math.atan(y / x));
    }

    public double getNewY(double x,double y) {
        double sqrt = Math.sqrt(x * x + y * y);
        return -sqrt * Math.cos(sqrt * Math.atan(y / x));
    }
}
