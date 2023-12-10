package edu.project4.renderer;

import edu.project4.model.FractalImage;
import edu.project4.model.SymmetryType;
import edu.project4.transformation.AffineTransformation;
import edu.project4.transformation.Transformation;
import java.util.List;

public class SingleThreadRenderer extends Renderer {

    @Override
    public void render(
        FractalImage fractalImage,
        List<AffineTransformation> affineTransformations,
        List<Transformation> variations,
        int samples,
        int iterPerSample,
        SymmetryType symmetryType
    ) {
        for (int num = 0; num < samples; num++) {
            iterate(fractalImage, variations, affineTransformations, iterPerSample);
        }
        doCorrectionToFractalImage(fractalImage);
        makeFractalImageSymmetrical(fractalImage, symmetryType);
    }
}
