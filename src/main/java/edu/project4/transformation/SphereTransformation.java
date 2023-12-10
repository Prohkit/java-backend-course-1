package edu.project4.transformation;

import edu.project4.model.Point;

public class SphereTransformation implements Transformation {
    private double getNewX(double x, double y) {
        return x / (x * x + y * y);
    }

    private double getNewY(double x, double y) {
        return y / (x * x + y * y);
    }

    @Override
    public Point apply(Point point) {
        return new Point(getNewX(point.getX(), point.getY()), getNewY(point.getX(), point.getY()));
    }
}
