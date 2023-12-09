package edu.project4.Transformations;

public class SphereTransformation {
    public double getNewX(double x, double y) {
        return x / (x * x + y * y);
    }

    public double getNewY(double x,double y) {
        return y / (x * x + y * y);
    }
}
