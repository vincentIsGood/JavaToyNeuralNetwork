package com.vincentcodes.ml.layers.cnn;

import com.vincentcodes.math.MatrixStacked3D;
import com.vincentcodes.ml.layers.MLModelLayer;

//TODO: not now
public class MLModelFlattenLayer extends MLModelLayer {

    @Override
    public MatrixStacked3D forward(MatrixStacked3D prevLayerInput) {
        return output = prevLayerInput.flatten();
    }

    @Override
    public MatrixStacked3D backward(MatrixStacked3D nextLayerError) {
        return nextLayerError;
    }
}
