package edu.project4.Transformations;

import edu.project4.Point;

public class HeartTransformation implements Transformation {
    private double getNewX(double x, double y) {
        double sqrt = Math.sqrt(x * x + y * y);
        return sqrt * Math.sin(sqrt * Math.atan(y / x));
    }

    private double getNewY(double x,double y) {
        double sqrt = Math.sqrt(x * x + y * y);
        return -sqrt * Math.cos(sqrt * Math.atan(y / x));
    }

    @Override
    public Point apply(Point point) {
        return new Point(getNewX(point.getX(), point.getY()), getNewY(point.getX(), point.getY()));
    }
}
