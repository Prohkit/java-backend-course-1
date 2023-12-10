package edu.project4.renderers;

import edu.project4.model.FractalImage;
import edu.project4.transformations.AffineTransformation;
import edu.project4.transformations.Transformation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadRenderer extends Renderer {

    @Override
    public void render(
        FractalImage fractalImage,
        List<AffineTransformation> affineTransformations,
        List<Transformation> variations,
        int samples,
        int iterPerSample
    ) {
        int threadsCount = Runtime.getRuntime().availableProcessors();
        int samplesPerThread = samples / threadsCount;
        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            executorService.submit(() ->
                multiThreadRender(
                    fractalImage,
                    affineTransformations,
                    variations,
                    samplesPerThread,
                    iterPerSample
                ));
        }
        executorService.close();
    }

    private void multiThreadRender(
        FractalImage fractalImage,
        List<AffineTransformation> affineTransformations,
        List<Transformation> variations,
        int samples,
        int iterPerSample
    ) {
        for (int num = 0; num < samples; num++) {
            iterate(fractalImage, variations, affineTransformations, iterPerSample);
        }
    }
}
