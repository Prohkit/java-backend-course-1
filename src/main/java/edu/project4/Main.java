package edu.project4;

import edu.project4.Transformations.PolarTransformation;
import edu.project4.Transformations.SinusoidalTransformation;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {

        Pixel[][] result = render(1000000, 150, 500, 1920, 1080);
        result = correction(1920, 1080, result);
        createIMG(result, 1920, 1080);
    }

    public static Pixel[][] render(int n, int eqCount, int it, int xRes, int yRes)
    {
        Pixel[][] pixels = fillPixels(xRes, yRes);
        Random random = new Random();
        // Генерируем eqCount аффинных преобразований со стартовыми цветами;
        List<AffineTransformation> affineTransformations = generateAffineTransformations(eqCount);

        for(int num=0; num<n; num++)
        {
            //Для изображения размером 1920х1080 можно
            //взять XMIN=-1.777,XMAX=1.777,YMIN=-1,YMAX=1
            double xMin = -1.777;
            double xMax = 1.777;
            double yMin = -1;
            double yMax = 1;
            //В этом случае в большинстве нелинейных преобразований с боков не будет оставаться черных областей
            double newX= ThreadLocalRandom.current().nextDouble(xMin,xMax);
            double newY=ThreadLocalRandom.current().nextDouble(yMin,xMax);
            //Первые 20 итераций точку не рисуем, т.к. сначала надо найти начальную
            for(int step=-20; step<it; step++)
            {
                //Выбираем одно из аффинных преобразований
                int i=ThreadLocalRandom.current().nextInt(0,eqCount);
                //и применяем его
                double x=affineTransformations.get(i).getA()*newX+affineTransformations.get(i).getB()*newY+affineTransformations.get(i).getC();
                double y=affineTransformations.get(i).getD()*newX+affineTransformations.get(i).getE()*newY+affineTransformations.get(i).getF();

                // Применяем нелинейное преобразование;
                PolarTransformation polarTransformation = new PolarTransformation();
                newX = polarTransformation.getNewX(x, y);
                newY = polarTransformation.getNewY(x, y);

                if(step>=0 && (newX >= -1 && newX <= 1) && (newY >= -1 && newY <= 1))
                {
                    //Вычисляем координаты точки, а затем задаем цвет
                    //Trunc??
                    int x1=(int)(xRes-(((xMax-newX)/(xMax-xMin))*xRes));
                    int y1=(int)(yRes-(((yMax-newY)/(yMax-yMin))*yRes));
                    //Если точка попала в область изображения
                    if(x1<xRes && y1<yRes)
                    {
                        //то проверяем, первый ли раз попали в нее
                        if(pixels[x1][y1].getHitCount()==0)
                        {
                            //Попали в первый раз, берем стартовый цвет у соответствующего аффинного преобразования
                            pixels[x1][y1].setRed(affineTransformations.get(i).getRed());
                            pixels[x1][y1].setGreen(affineTransformations.get(i).getGreen());
                            pixels[x1][y1].setBlue(affineTransformations.get(i).getBlue());
                        } else
                        {
                            //Попали не в первый раз, считаем так:
                            pixels[x1][y1].setRed((pixels[x1][y1].getRed()+affineTransformations.get(i).getRed())/2);
                            pixels[x1][y1].setGreen((pixels[x1][y1].getGreen()+affineTransformations.get(i).getGreen())/2);
                            pixels[x1][y1].setBlue((pixels[x1][y1].getBlue()+affineTransformations.get(i).getBlue())/2);
                        }
                        //Увеличиваем счетчик точки на единицу
                        pixels[x1][y1].incrementHitCount();
                    }
                }
            }
        }
        return pixels;
    }

    private static List<AffineTransformation> generateAffineTransformations(int transformationsCount) {
        List<AffineTransformation> affineTransformations = new ArrayList<>();
        for (int i = 0; i < transformationsCount; i++) {
            affineTransformations.add(new AffineTransformation());
        }
        return affineTransformations;
    }

    private static Pixel[][] fillPixels(int xRes, int yRes) {
        Pixel[][] pixels = new Pixel[xRes][yRes];
        for (int x = 0; x < xRes; x++) {
            for (int y = 0; y < yRes; y++) {
                pixels[x][y] = new Pixel(x, y);
            }
        }
        return pixels;
    }

    static Pixel[][] correction(int xRes, int yRes, Pixel[][] pixels)
    {
        double max=0.0;
        double gamma=2.2;
        for (int row=0; row<xRes; row++) {
            for (int col = 0; col < yRes; col++) {
                if (pixels[row][col].getHitCount() != 0) {
                    pixels[row][col].setNormal(Math.log10(pixels[row][col].getHitCount()));
                    if (pixels[row][col].getNormal() > max) {
                        max = pixels[row][col].getNormal();
                    }
                }
            }
        }
        for (int row=0; row<xRes; row++) {
            for (int col=0; col<yRes; col++) {
            {
                pixels[row][col].setNormal(pixels[row][col].getNormal() / max);
                pixels[row][col].setRed((int) (pixels[row][col].getRed()*Math.pow(pixels[row][col].getNormal(),(1.0 / gamma))));
                pixels[row][col].setGreen((int) (pixels[row][col].getGreen()*Math.pow(pixels[row][col].getNormal(),(1.0 / gamma))));
                pixels[row][col].setBlue((int) (pixels[row][col].getBlue()*Math.pow(pixels[row][col].getNormal(),(1.0 / gamma))));
            }
            }
        }
        return pixels;
    }

    private static void createIMG(Pixel[][] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Pixel pixel = pixels[x][y];
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
