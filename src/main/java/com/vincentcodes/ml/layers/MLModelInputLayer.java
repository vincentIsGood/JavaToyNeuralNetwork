package com.vincentcodes.ml.layers;

import com.vincentcodes.math.Matrix;

public class MLModelInputLayer extends MLModelLayer {
    public MLModelInputLayer(int inputDim) {
        super(inputDim, inputDim);
    }

    public Matrix forward(Matrix prevLayerInput){
        return output = prevLayerInput;
    }

    /**
     * @return error calculated from this layer
     */
    public Matrix backward(Matrix nextLayerError){
        return nextLayerError;
    }
}
