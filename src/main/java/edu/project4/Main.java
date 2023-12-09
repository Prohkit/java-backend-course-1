package edu.project4;

import edu.project4.Transformations.PolarTransformation;
import edu.project4.Transformations.Transformation;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        int w = 1920;
        int h = 1080;

        FractalImage fractalImage = render(1000000, 150, 500, w, h);
        fractalImage = correction(fractalImage);
        createIMG(fractalImage);
    }

    public static FractalImage render(int n, int eqCount, int it, int width, int height) {
        FractalImage fractalImage = FractalImage.create(width, height);
        Random random = new Random();
        // Генерируем eqCount аффинных преобразований со стартовыми цветами;
        List<AffineTransformation> affineTransformations = generateAffineTransformations(eqCount);

        for (int num = 0; num < n; num++) {
            //Для изображения размером 1920х1080 можно
            //взять XMIN=-1.777,XMAX=1.777,YMIN=-1,YMAX=1
            double xMin = -1.777;
            double xMax = 1.777;
            double yMin = -1;
            double yMax = 1;
            //В этом случае в большинстве нелинейных преобразований с боков не будет оставаться черных областей
            Point newPoint = new Point(
                ThreadLocalRandom.current().nextDouble(xMin, xMax),
                ThreadLocalRandom.current().nextDouble(yMin, xMax)
            );
            //Первые 20 итераций точку не рисуем, т.к. сначала надо найти начальную
            for (int step = -20; step < it; step++) {
                //Выбираем одно из аффинных преобразований
                int i = ThreadLocalRandom.current().nextInt(0, eqCount);
                AffineTransformation affineTransformation = affineTransformations.get(i);
                //и применяем его
                Point pointWithAffineTransformation = new Point(
                    affineTransformation.getA() * newPoint.getX() + affineTransformation.getB() * newPoint.getY() +
                        affineTransformation.getC(),
                    affineTransformation.getD() * newPoint.getX() + affineTransformation.getE() * newPoint.getY() +
                        affineTransformation.getF()
                );

                // Применяем нелинейное преобразование;
                //
                Transformation polarTransformation = new PolarTransformation();
                newPoint = polarTransformation.apply(pointWithAffineTransformation);

                boolean isNewXBetweenXMinAndXMax = newPoint.getX() >= xMin && newPoint.getX() <= xMax;
                boolean isNewXBetweenYMinAndYMax = newPoint.getY() >= yMin && newPoint.getY() <= yMax;
                if (step >= 0 && isNewXBetweenXMinAndXMax && isNewXBetweenYMinAndYMax) {
                    //Вычисляем координаты точки, а затем задаем цвет
                    //Trunc??
                    Coordinate coordinate = new Coordinate(
                        (int) (width - (((xMax - newPoint.getX()) / (xMax - xMin)) * width)),
                        (int) (height - (((yMax - newPoint.getY()) / (yMax - yMin)) * height))
                    );

                    //Если точка попала в область изображения
                    if (coordinate.getX() < width && coordinate.getY() < height) {
                        //то проверяем, первый ли раз попали в нее
                        if (fractalImage.getPixel(coordinate.getX(), coordinate.getY()).getHitCount() == 0) {
                            //Попали в первый раз, берем стартовый цвет у соответствующего аффинного преобразования
                            fractalImage.getPixel(coordinate.getX(), coordinate.getY())
                                .setRed(affineTransformation.getRed());
                            fractalImage.getPixel(coordinate.getX(), coordinate.getY())
                                .setGreen(affineTransformation.getGreen());
                            fractalImage.getPixel(coordinate.getX(), coordinate.getY())
                                .setBlue(affineTransformation.getBlue());
                        } else {
                            //Попали не в первый раз, считаем так:
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
                        //Увеличиваем счетчик точки на единицу
                        fractalImage.getPixel(coordinate.getX(), coordinate.getY()).incrementHitCount();
                    }
                }
            }
        }
        return fractalImage;
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
