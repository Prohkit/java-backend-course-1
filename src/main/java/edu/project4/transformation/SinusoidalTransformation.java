package edu.project4.transformation;

import edu.project4.model.Point;

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
