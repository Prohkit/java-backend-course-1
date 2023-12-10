package edu.project4;

import edu.project4.model.FractalImage;
import edu.project4.model.SymmetryType;
import edu.project4.renderer.MultiThreadRenderer;
import edu.project4.renderer.SingleThreadRenderer;
import edu.project4.transformation.AffineTransformation;
import edu.project4.transformation.DiskTransformation;
import edu.project4.transformation.HeartTransformation;
import edu.project4.transformation.PolarTransformation;
import edu.project4.transformation.SinusoidalTransformation;
import edu.project4.transformation.SphereTransformation;
import edu.project4.transformation.Transformation;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Test {
    private static final int WIDTH = 1920;
    private static final int HEIGHT = 1080;
    private static final int EQ_COUNT = 25;
    private static final int SAMPLES = 100000;
    private static final int ITERATIONS = 50;
    private static final Logger LOGGER = LogManager.getLogger();

    @org.junit.jupiter.api.Test
    @DisplayName("Многопоточная реализация выполняется быстрее однопоточной")
    void multiThreadVersionIsFasterThanSingleThreadVersion() {
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
        long singleThreadRenderingWorkingTime = singleThreadRenderingDoneTime - singleThreadRenderingBeginTime;
        long multiThreadRenderingWorkingTime = multiThreadRenderingDoneTime - multiThreadRenderingBeginTime;
        LOGGER.info("Время выполнения однопоточной реализации: "
            + (singleThreadRenderingWorkingTime));
        LOGGER.info("Время выполнения многопоточной реализации: "
            + (multiThreadRenderingWorkingTime));

        assertThat(multiThreadRenderingWorkingTime)
            .isLessThan(singleThreadRenderingWorkingTime);
    }
}
