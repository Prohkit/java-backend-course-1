package edu.project4;

import edu.project4.renderers.SingleThreadRenderer;
import edu.project4.saver.ImageFormat;
import edu.project4.saver.ImageUtils;
import edu.project4.transformations.DiskTransformation;
import edu.project4.transformations.HeartTransformation;
import edu.project4.transformations.PolarTransformation;
import edu.project4.transformations.SinusoidalTransformation;
import edu.project4.transformations.SphereTransformation;
import edu.project4.transformations.Transformation;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;
        int eqCount = 25;

        FractalImage fractalImage = FractalImage.create(width, height);
        SingleThreadRenderer renderer = new SingleThreadRenderer();
        List<AffineTransformation> affineTransformations = renderer.generateAffineTransformations(eqCount);
        List<Transformation> transformations = List.of(
            new DiskTransformation(),
            new HeartTransformation(),
            new SinusoidalTransformation(),
            new PolarTransformation(),
            new SphereTransformation()
        );
        renderer.render(fractalImage, affineTransformations, transformations, 100000, 50);
        renderer.doCorrectionToFractalImage(fractalImage);
        ImageUtils.save(fractalImage, Path.of("asdf"), ImageFormat.BMP);
    }
}
