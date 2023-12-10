package edu.project4.transformation;

import edu.project4.model.Point;
import static java.lang.Math.PI;
import static java.lang.Math.atan;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class DiskTransformation implements Transformation {
    private double getNewX(double x, double y) {
        return (1 / PI) * atan(y / x) * sin(PI * sqrt(x * x + y * y));
    }

    private double getNewY(double x, double y) {
        return (1 / PI) * atan(y / x) * cos(PI * sqrt(x * x + y * y));
    }

    @Override
    public Point apply(Point point) {
        return new Point(getNewX(point.getX(), point.getY()), getNewY(point.getX(), point.getY()));
    }
}
