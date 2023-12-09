package edu.project4.Transformations;

import edu.project4.Point;

public class SinusoidalTransformation implements Transformation {

    private double getNewX(double x) {
        return Math.sin(x);
    }

    private double getNewY(double y) {
        return Math.sin(y);
    }

    @Override
    public Point apply(Point point) {
        return new Point(getNewX(point.getX()), getNewY(point.getY()));
    }
}
