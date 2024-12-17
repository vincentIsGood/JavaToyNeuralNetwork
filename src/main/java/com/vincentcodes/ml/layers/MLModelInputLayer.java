package com.vincentcodes.ml.layers;

import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.math.MatrixStacked3D;

public class MLModelInputLayer extends MLModelLayer {
    public MLModelInputLayer(int inputDim) {
    }

    public MatrixStacked3D forward(MatrixStacked3D prevLayerInput){
        return output = prevLayerInput;
    }

    /**
     * @return error calculated from this layer
     */
    public MatrixStacked3D backward(MatrixStacked3D nextLayerError){
        return nextLayerError;
    }
}
