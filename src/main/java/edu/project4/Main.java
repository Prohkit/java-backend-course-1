package edu.project4;

import edu.project4.model.FractalImage;
import edu.project4.renderers.MultiThreadRenderer;
import edu.project4.renderers.SingleThreadRenderer;
import edu.project4.saver.ImageFormat;
import edu.project4.saver.ImageUtils;
import edu.project4.transformations.AffineTransformation;
import edu.project4.transformations.DiskTransformation;
import edu.project4.transformations.HeartTransformation;
import edu.project4.transformations.PolarTransformation;
import edu.project4.transformations.SinusoidalTransformation;
import edu.project4.transformations.SphereTransformation;
import edu.project4.transformations.Transformation;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int width = 1920;
        int height = 1080;
        int eqCount = 25;

        FractalImage fractalImage = FractalImage.create(width, height);
        FractalImage fractalImageMulti = FractalImage.create(width, height);
        SingleThreadRenderer singleThreadRenderer = new SingleThreadRenderer();
        List<AffineTransformation> affineTransformations = singleThreadRenderer.generateAffineTransformations(eqCount);
        List<Transformation> transformations = List.of(
            new DiskTransformation(),
            new HeartTransformation(),
            new SinusoidalTransformation(),
            new PolarTransformation(),
            new SphereTransformation()
        );
        long a = System.currentTimeMillis();
        singleThreadRenderer.render(fractalImage, affineTransformations, transformations, 1000000, 50);
        long b = System.currentTimeMillis();
        MultiThreadRenderer multiThreadRenderer = new MultiThreadRenderer();
        long c = System.currentTimeMillis();
        multiThreadRenderer.render(fractalImageMulti, affineTransformations, transformations, 1000000, 50);
        long d = System.currentTimeMillis();
        System.out.println(b - a);
        System.out.println(d - c);
        singleThreadRenderer.doCorrectionToFractalImage(fractalImage);
        multiThreadRenderer.doCorrectionToFractalImage(fractalImageMulti);
        ImageUtils.save(fractalImage, Path.of("single"), ImageFormat.JPEG);
        ImageUtils.save(fractalImageMulti, Path.of("multi"), ImageFormat.JPEG);
    }
}
