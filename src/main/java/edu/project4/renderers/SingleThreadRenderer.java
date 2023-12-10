package edu.project4.renderers;

import edu.project4.AffineTransformation;
import edu.project4.Coordinate;
import edu.project4.FractalImage;
import edu.project4.Point;
import edu.project4.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SingleThreadRenderer {
    public void render(
        FractalImage fractalImage,
        List<AffineTransformation> affineTransformations,
        List<Transformation> variations,
        int samples,
        int iterPerSample
    ) {
        double xMin = -1.777;
        double xMax = 1.777;
        double yMin = -1;
        double yMax = 1;
        for (int num = 0; num < samples; num++) {
            Point newPoint = getNewPoint(xMin, xMax, yMin, yMax);
            for (int step = -20; step < iterPerSample; step++) {
                AffineTransformation affineTransformation = getAffineTransformation(affineTransformations);
                Point pointWithAffineTransformation = applyAffineTransformation(affineTransformation, newPoint);
                Transformation transformation = getNonLinearTransformation(variations);
                newPoint = transformation.apply(pointWithAffineTransformation);

                boolean isNewXBetweenXMinAndXMax = newPoint.getX() >= xMin && newPoint.getX() <= xMax;
                boolean isNewXBetweenYMinAndYMax = newPoint.getY() >= yMin && newPoint.getY() <= yMax;
                if (step >= 0 && isNewXBetweenXMinAndXMax && isNewXBetweenYMinAndYMax) {
                    Coordinate coordinate = getPointCoordinate(fractalImage, newPoint, xMax, xMin, yMax, yMin);
                    boolean isTheCoordinateEnteredToTheImage =
                        coordinate.getX() < fractalImage.getWidth() && coordinate.getY() < fractalImage.getHeight();
                    if (isTheCoordinateEnteredToTheImage) {
                        processPixel(fractalImage, coordinate, affineTransformation);
                    }
                }
            }
        }
    }

    private void processPixel(
        FractalImage fractalImage,
        Coordinate coordinate,
        AffineTransformation affineTransformation
    ) {
        if (fractalImage.getPixel(coordinate.getX(), coordinate.getY()).getHitCount() == 0) {
            setColorsFirstTime(fractalImage, coordinate, affineTransformation);
        } else {
            setColorsNotTheFirstTime(fractalImage, coordinate, affineTransformation);
        }
        fractalImage.getPixel(coordinate.getX(), coordinate.getY()).incrementHitCount();
    }

    private void setColorsNotTheFirstTime(
        FractalImage fractalImage,
        Coordinate coordinate,
        AffineTransformation affineTransformation
    ) {
        fractalImage.getPixel(coordinate.getX(), coordinate.getY()).setRed(
            (fractalImage.getPixel(coordinate.getX(), coordinate.getY()).getRed() +
                affineTransformation.getRed()) / 2);
        fractalImage.getPixel(coordinate.getX(), coordinate.getY()).setGreen(
            (fractalImage.getPixel(coordinate.getX(), coordinate.getY()).getGreen() +
                affineTransformation.getGreen()) /
                2);
        fractalImage.getPixel(coordinate.getX(), coordinate.getY()).setBlue(
            (fractalImage.getPixel(coordinate.getX(), coordinate.getY()).getBlue() +
                affineTransformation.getBlue()) / 2);
    }

    private void setColorsFirstTime(
        FractalImage fractalImage,
        Coordinate coordinate,
        AffineTransformation affineTransformation
    ) {
        fractalImage.getPixel(coordinate.getX(), coordinate.getY())
            .setRed(affineTransformation.getRed());
        fractalImage.getPixel(coordinate.getX(), coordinate.getY())
            .setGreen(affineTransformation.getGreen());
        fractalImage.getPixel(coordinate.getX(), coordinate.getY())
            .setBlue(affineTransformation.getBlue());
    }

    private Coordinate getPointCoordinate(
        FractalImage fractalImage,
        Point newPoint,
        double xMax,
        double xMin,
        double yMax,
        double yMin
    ) {
        return new Coordinate(
            (int) (fractalImage.getWidth() -
                (((xMax - newPoint.getX()) / (xMax - xMin)) * fractalImage.getWidth())),
            (int) (fractalImage.getHeight() - (((yMax - newPoint.getY()) / (yMax - yMin)) *
                fractalImage.getHeight()))
        );
    }

    private Transformation getNonLinearTransformation(List<Transformation> variations) {
        return variations.get(ThreadLocalRandom.current().nextInt(0, variations.size()));
    }

    private Point applyAffineTransformation(AffineTransformation affineTransformation, Point newPoint) {
        //и применяем его
        return new Point(
            affineTransformation.getA() * newPoint.getX() + affineTransformation.getB() * newPoint.getY() +
                affineTransformation.getC(),
            affineTransformation.getD() * newPoint.getX() + affineTransformation.getE() * newPoint.getY() +
                affineTransformation.getF()
        );
    }

    private AffineTransformation getAffineTransformation(List<AffineTransformation> affineTransformations) {
        return affineTransformations.get(ThreadLocalRandom.current().nextInt(0, affineTransformations.size()));
    }

    private Point getNewPoint(double xMin, double xMax, double yMin, double yMax) {
        return new Point(
            ThreadLocalRandom.current().nextDouble(xMin, xMax),
            ThreadLocalRandom.current().nextDouble(yMin, yMax)
        );
    }

    public void doCorrectionToFractalImage(FractalImage fractalImage) {
        int width = fractalImage.getWidth();
        int height = fractalImage.getHeight();
        double max = 0.0;
        double gamma = 2.2;
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                if (fractalImage.getPixel(row, col).getHitCount() != 0) {
                    doLogarithmicCorrection(fractalImage, row, col);
                    if (fractalImage.getPixel(row, col).getNormal() > max) {
                        max = fractalImage.getPixel(row, col).getNormal();
                    }
                }
            }
        }
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                {
                    doGammaCorrection(fractalImage, row, col, max, gamma);
                }
            }
        }
    }

    private void doLogarithmicCorrection(FractalImage fractalImage, int row, int col) {
        fractalImage.getPixel(row, col)
            .setNormal(Math.log10(fractalImage.getPixel(row, col).getHitCount()));
    }

    private void doGammaCorrection(
        FractalImage fractalImage,
        int row,
        int col,
        double max,
        double gamma
    ) {
        fractalImage.getPixel(row, col)
            .setNormal(fractalImage.getPixel(row, col).getNormal() / max);

        fractalImage.getPixel(row, col)
            .setRed((int) (fractalImage.getPixel(row, col).getRed() *
                Math.pow(fractalImage.getPixel(row, col).getNormal(), (1.0 / gamma))));

        fractalImage.getPixel(row, col)
            .setGreen((int) (fractalImage.getPixel(row, col).getGreen() *
                Math.pow(fractalImage.getPixel(row, col).getNormal(), (1.0 / gamma))));

        fractalImage.getPixel(row, col)
            .setBlue((int) (fractalImage.getPixel(row, col).getBlue() *
                Math.pow(fractalImage.getPixel(row, col).getNormal(), (1.0 / gamma))));
    }

    public List<AffineTransformation> generateAffineTransformations(int transformationsCount) {
        List<AffineTransformation> affineTransformations = new ArrayList<>();
        for (int i = 0; i < transformationsCount; i++) {
            affineTransformations.add(new AffineTransformation());
        }
        return affineTransformations;
    }
}
