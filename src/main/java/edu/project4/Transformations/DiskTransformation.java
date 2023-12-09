package edu.project4.Transformations;

import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class DiskTransformation {
    public double getNewX(double x, double y) {
        return (1 / PI) * atan(y / x) * sin(PI * sqrt(x * x + y * y));
    }

    public double getNewY(double x, double y) {
        return (1 / PI) * atan(y / x) * cos(PI * sqrt(x * x + y * y));
    }
}
