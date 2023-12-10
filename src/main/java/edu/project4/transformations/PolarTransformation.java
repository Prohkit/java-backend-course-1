package edu.project4.transformations;

import edu.project4.model.Point;

public class PolarTransformation implements Transformation {
    private double getNewX(double x, double y) {
        return Math.atan(y / x) / Math.PI;
    }

    private double getNewY(double x, double y) {
        return Math.sqrt(x * x + y * y) - 1;
    }

    @Override
    public Point apply(Point point) {
        return new Point(getNewX(point.getX(), point.getY()), getNewY(point.getX(), point.getY()));
    }
}
