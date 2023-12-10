package edu.project4;

import edu.project4.Transformations.DiskTransformation;
import edu.project4.Transformations.HeartTransformation;
import edu.project4.Transformations.PolarTransformation;
import edu.project4.Transformations.SinusoidalTransformation;
import edu.project4.Transformations.SphereTransformation;
import edu.project4.Transformations.Transformation;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;
        int eqCount = 25;

        FractalImage fractalImage = FractalImage.create(width, height);
        List<AffineTransformation> affineTransformations = generateAffineTransformations(eqCount);
        List<Transformation> transformations = List.of(
            new DiskTransformation(),
            new HeartTransformation(),
            new SinusoidalTransformation(),
            new PolarTransformation(),
            new SphereTransformation()
        );
        render(fractalImage, affineTransformations, transformations, 100000, 50);
        correction(fractalImage);
        createIMG(fractalImage);
    }

    public static FractalImage render(
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
        return fractalImage;
    }

    private static FractalImage processPixel(
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
        return fractalImage;
    }

    private static FractalImage setColorsNotTheFirstTime(
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
        return fractalImage;
    }

    private static FractalImage setColorsFirstTime(
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
        return fractalImage;
    }

    private static Coordinate getPointCoordinate(
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

    private static Transformation getNonLinearTransformation(List<Transformation> variations) {
        return variations.get(ThreadLocalRandom.current().nextInt(0, variations.size()));
    }

    private static Point applyAffineTransformation(AffineTransformation affineTransformation, Point newPoint) {
        //и применяем его
        return new Point(
            affineTransformation.getA() * newPoint.getX() + affineTransformation.getB() * newPoint.getY() +
                affineTransformation.getC(),
            affineTransformation.getD() * newPoint.getX() + affineTransformation.getE() * newPoint.getY() +
                affineTransformation.getF()
        );
    }

    private static AffineTransformation getAffineTransformation(List<AffineTransformation> affineTransformations) {
        return affineTransformations.get(ThreadLocalRandom.current().nextInt(0, affineTransformations.size()));
    }

    private static Point getNewPoint(double xMin, double xMax, double yMin, double yMax) {
        return new Point(
            ThreadLocalRandom.current().nextDouble(xMin, xMax),
            ThreadLocalRandom.current().nextDouble(yMin, yMax)
        );
    }

    private static List<AffineTransformation> generateAffineTransformations(int transformationsCount) {
        List<AffineTransformation> affineTransformations = new ArrayList<>();
        for (int i = 0; i < transformationsCount; i++) {
            affineTransformations.add(new AffineTransformation());
        }
        return affineTransformations;
    }

    static FractalImage correction(FractalImage fractalImage) {
        int width = fractalImage.getWidth();
        int height = fractalImage.getHeight();
        double max = 0.0;
        double gamma = 2.2;
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                if (fractalImage.getPixel(row, col).getHitCount() != 0) {
                    fractalImage.getPixel(row, col)
                        .setNormal(Math.log10(fractalImage.getPixel(row, col).getHitCount()));
                    if (fractalImage.getPixel(row, col).getNormal() > max) {
                        max = fractalImage.getPixel(row, col).getNormal();
                    }
                }
            }
        }
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                {
                    fractalImage.getPixel(row, col).setNormal(fractalImage.getPixel(row, col).getNormal() / max);
                    fractalImage.getPixel(row, col).setRed((int) (fractalImage.getPixel(row, col).getRed() *
                        Math.pow(fractalImage.getPixel(row, col).getNormal(), (1.0 / gamma))));
                    fractalImage.getPixel(row, col).setGreen((int) (fractalImage.getPixel(row, col).getGreen() *
                        Math.pow(fractalImage.getPixel(row, col).getNormal(), (1.0 / gamma))));
                    fractalImage.getPixel(row, col).setBlue((int) (fractalImage.getPixel(row, col).getBlue() *
                        Math.pow(fractalImage.getPixel(row, col).getNormal(), (1.0 / gamma))));
                }
            }
        }
        return fractalImage;
    }

    private static void createIMG(FractalImage fractalImage) {
        int width = fractalImage.getWidth();
        int height = fractalImage.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = fractalImage.getPixel(x, y);
                graphics.setColor(new Color(pixel.getRed(), pixel.getGreen(), pixel.getBlue()));
                graphics.fillRect(x, y, 1, 1);
            }
        }

        try {
            ImageIO.write(image, "png", new File("img0.png"));
        } catch (IOException e) {
        }
    }
}
