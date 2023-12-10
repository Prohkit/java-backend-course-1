package edu.project4.renderers;

import edu.project4.model.FractalImage;
import edu.project4.transformations.AffineTransformation;
import edu.project4.transformations.Transformation;
import java.util.List;

public class SingleThreadRenderer extends Renderer {

    @Override
    public void render(
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
