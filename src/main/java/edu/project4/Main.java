package edu.project4;

import edu.project4.model.FractalImage;
import edu.project4.model.SymmetryType;
import edu.project4.renderer.MultiThreadRenderer;
import edu.project4.renderer.SingleThreadRenderer;
import edu.project4.saver.ImageFormat;
import edu.project4.saver.ImageUtils;
import edu.project4.transformation.AffineTransformation;
import edu.project4.transformation.DiskTransformation;
import edu.project4.transformation.HeartTransformation;
import edu.project4.transformation.PolarTransformation;
import edu.project4.transformation.SinusoidalTransformation;
import edu.project4.transformation.SphereTransformation;
import edu.project4.transformation.Transformation;
import java.nio.file.Path;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final int EQ_COUNT = 25;
    private static final int SAMPLES = 100000;
    private static final int ITERATIONS = 500;
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
        singleThreadRenderer.render(
            fractalImage,
            affineTransformations,
            transformations,
            SAMPLES,
            ITERATIONS,
            SymmetryType.NONE
        );
        long singleThreadRenderingDoneTime = System.currentTimeMillis();

        MultiThreadRenderer multiThreadRenderer = new MultiThreadRenderer();
        long multiThreadRenderingBeginTime = System.currentTimeMillis();
        multiThreadRenderer.render(
            fractalImageMulti,
            affineTransformations,
            transformations,
            SAMPLES,
            ITERATIONS,
            SymmetryType.NONE
        );
        long multiThreadRenderingDoneTime = System.currentTimeMillis();

        LOGGER.info("Время выполнения однопоточной реализации: "
            + (singleThreadRenderingDoneTime - singleThreadRenderingBeginTime));
        LOGGER.info("Время выполнения многопоточной реализации: "
            + (multiThreadRenderingDoneTime - multiThreadRenderingBeginTime));

        ImageUtils.save(fractalImage, Path.of("single"), ImageFormat.JPEG);
        ImageUtils.save(fractalImageMulti, Path.of("multi"), ImageFormat.JPEG);
    }
}
