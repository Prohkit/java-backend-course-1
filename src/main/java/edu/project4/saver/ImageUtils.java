package edu.project4.saver;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private ImageUtils() {
    }

    public static void save(FractalImage fractalImage, Path filename, ImageFormat format) {
        BufferedImage image = getImage(fractalImage);
        try {
            String correctFileName = filename.toString() + '.' + format.toString();
            ImageIO.write(image, format.toString(), new File(correctFileName));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static BufferedImage getImage(FractalImage fractalImage) {
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
        return image;
    }
}
