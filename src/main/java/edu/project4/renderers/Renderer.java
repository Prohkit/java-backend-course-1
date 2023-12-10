package edu.project4.renderers;

import edu.project4.model.Coordinate;
import edu.project4.model.FractalImage;
import edu.project4.model.Point;
import edu.project4.transformations.AffineTransformation;
import edu.project4.transformations.Transformation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Renderer {
    private static final double X_MIN = -1.777;
    private static final double X_MAX = 1.777;
    private static final double Y_MIN = -1;
    private static final double Y_MAX = 1;
    private static final int OFFSET = -20;

    public abstract void render(
        FractalImage fractalImage,
        List<AffineTransformation> affineTransformations,
        List<Transformation> variations,
        int samples,
        int iterPerSample
    );

    protected void iterate(
        FractalImage fractalImage,
        List<Transformation> variations,
        List<AffineTransformation> affineTransformations,
        int iterPerSample
    ) {
        Point newPoint = getNewPoint();
        for (int step = OFFSET; step < iterPerSample; step++) {
            AffineTransformation affineTransformation = getAffineTransformation(affineTransformations);
            Point pointWithAffineTransformation = applyAffineTransformation(affineTransformation, newPoint);
            Transformation transformation = getNonLinearTransformation(variations);
            newPoint = transformation.apply(pointWithAffineTransformation);

            boolean isNewXBetweenXMinAndXMax = newPoint.getX() >= X_MIN && newPoint.getX() <= X_MAX;
            boolean isNewXBetweenYMinAndYMax = newPoint.getY() >= Y_MIN && newPoint.getY() <= Y_MAX;
            if (step >= 0 && isNewXBetweenXMinAndXMax && isNewXBetweenYMinAndYMax) {
                Coordinate coordinate = getPointCoordinate(fractalImage, newPoint);
                boolean isTheCoordinateEnteredToTheImage =
                    coordinate.getX() < fractalImage.getWidth() && coordinate.getY() < fractalImage.getHeight();
                if (isTheCoordinateEnteredToTheImage) {
                    processPixel(fractalImage, coordinate, affineTransformation);
                }
            }
        }
    }

    protected void processPixel(
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

    protected void setColorsNotTheFirstTime(
        FractalImage fractalImage,
        Coordinate coordinate,
        AffineTransformation affineTransformation
    ) {
        fractalImage.getPixel(coordinate.getX(), coordinate.getY()).setRed(
            (fractalImage.getPixel(coordinate.getX(), coordinate.getY()).getRed()
                + affineTransformation.getRed()) / 2);
        fractalImage.getPixel(coordinate.getX(), coordinate.getY()).setGreen(
            (fractalImage.getPixel(coordinate.getX(), coordinate.getY()).getGreen()
                + affineTransformation.getGreen()) / 2);
        fractalImage.getPixel(coordinate.getX(), coordinate.getY()).setBlue(
            (fractalImage.getPixel(coordinate.getX(), coordinate.getY()).getBlue()
                + affineTransformation.getBlue()) / 2);
    }

    protected void setColorsFirstTime(
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

    protected Coordinate getPointCoordinate(
        FractalImage fractalImage,
        Point newPoint
    ) {
        return new Coordinate(
            (int) (fractalImage.getWidth() - (((X_MAX - newPoint.getX()) / (X_MAX - X_MIN)) * fractalImage.getWidth())),
            (int) (fractalImage.getHeight()
                - (((Y_MAX - newPoint.getY()) / (Y_MAX - Y_MIN)) * fractalImage.getHeight()))
        );
    }

    protected Transformation getNonLinearTransformation(List<Transformation> variations) {
        return variations.get(ThreadLocalRandom.current().nextInt(0, variations.size()));
    }

    protected Point applyAffineTransformation(AffineTransformation affineTransformation, Point newPoint) {
        //и применяем его
        return new Point(
            affineTransformation.getA() * newPoint.getX() + affineTransformation.getB() * newPoint.getY()
                + affineTransformation.getC(),
            affineTransformation.getD() * newPoint.getX() + affineTransformation.getE() * newPoint.getY()
                + affineTransformation.getF()
        );
    }

    protected AffineTransformation getAffineTransformation(List<AffineTransformation> affineTransformations) {
        return affineTransformations.get(ThreadLocalRandom.current().nextInt(0, affineTransformations.size()));
    }

    protected Point getNewPoint() {
        return new Point(
            ThreadLocalRandom.current().nextDouble(X_MIN, X_MAX),
            ThreadLocalRandom.current().nextDouble(Y_MIN, Y_MAX)
        );
    }

    @SuppressWarnings("MagicNumber")
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
                doGammaCorrection(fractalImage, row, col, max, gamma);
            }
        }
    }

    protected void doLogarithmicCorrection(FractalImage fractalImage, int row, int col) {
        fractalImage.getPixel(row, col)
            .setNormal(Math.log10(fractalImage.getPixel(row, col).getHitCount()));
    }

    protected void doGammaCorrection(
        FractalImage fractalImage,
        int row,
        int col,
        double max,
        double gamma
    ) {
        fractalImage.getPixel(row, col)
            .setNormal(fractalImage.getPixel(row, col).getNormal() / max);

        fractalImage.getPixel(row, col)
            .setRed((int) (fractalImage.getPixel(row, col).getRed()
                * Math.pow(fractalImage.getPixel(row, col).getNormal(), (1.0 / gamma))));

        fractalImage.getPixel(row, col)
            .setGreen((int) (fractalImage.getPixel(row, col).getGreen()
                * Math.pow(fractalImage.getPixel(row, col).getNormal(), (1.0 / gamma))));

        fractalImage.getPixel(row, col)
            .setBlue((int) (fractalImage.getPixel(row, col).getBlue()
                * Math.pow(fractalImage.getPixel(row, col).getNormal(), (1.0 / gamma))));
    }

    public List<AffineTransformation> generateAffineTransformations(int transformationsCount) {
        List<AffineTransformation> affineTransformations = new ArrayList<>();
        for (int i = 0; i < transformationsCount; i++) {
            affineTransformations.add(new AffineTransformation());
        }
        return affineTransformations;
    }
}
