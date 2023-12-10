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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final int EQ_COUNT = 25;
    private static final int SAMPLES = 1000000;
    private static final int ITERATIONS = 50;
    private static final Logger LOGGER = LogManager.getLogger();

    private Main() {
    }

    public static void main(String[] args) {
        FractalImage fractalImage = FractalImage.create(WIDTH, HEIGHT);
        FractalImage fractalImageMulti = FractalImage.create(WIDTH, HEIGHT);
        SingleThreadRenderer singleThreadRenderer = new SingleThreadRenderer();
        List<AffineTransformation> affineTransformations = singleThreadRenderer.generateAffineTransformations(EQ_COUNT);
        List<Transformation> transformations = List.of(
            new DiskTransformation(),
            new HeartTransformation(),
            new SinusoidalTransformation(),
            new PolarTransformation(),
            new SphereTransformation()
        );

        long singleThreadRenderingBeginTime = System.currentTimeMillis();
        singleThreadRenderer.render(fractalImage, affineTransformations, transformations, SAMPLES, ITERATIONS);
        long singleThreadRenderingDoneTime = System.currentTimeMillis();

        MultiThreadRenderer multiThreadRenderer = new MultiThreadRenderer();
        long multiThreadRenderingBeginTime = System.currentTimeMillis();
        multiThreadRenderer.render(fractalImageMulti, affineTransformations, transformations, SAMPLES, ITERATIONS);
        long multiThreadRenderingDoneTime = System.currentTimeMillis();

        LOGGER.info("Время выполнения однопоточной реализации: "
            + (singleThreadRenderingDoneTime - singleThreadRenderingBeginTime));
        LOGGER.info("Время выполнения многопоточной реализации: "
            + (multiThreadRenderingDoneTime - multiThreadRenderingBeginTime));

        singleThreadRenderer.doCorrectionToFractalImage(fractalImage);
        multiThreadRenderer.doCorrectionToFractalImage(fractalImageMulti);

        ImageUtils.save(fractalImage, Path.of("single"), ImageFormat.JPEG);
        ImageUtils.save(fractalImageMulti, Path.of("multi"), ImageFormat.JPEG);
    }
}
