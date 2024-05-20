package com.vincentcodes.ml.layers;

import com.vincentcodes.math.Matrix2D;

public class MLModelInputLayer extends MLModelLayer {
    public MLModelInputLayer(int inputDim) {
    }

    public Matrix2D forward(Matrix2D prevLayerInput){
        return output = prevLayerInput;
    }

    /**
     * @return error calculated from this layer
     */
    public Matrix2D backward(Matrix2D nextLayerError){
        return nextLayerError;
    }
}
