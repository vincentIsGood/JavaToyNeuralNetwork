package com.vincentcodes.ml.layers;

import com.vincentcodes.math.Matrix;
import com.vincentcodes.ml.MLModel;

public abstract class MLModelLayer {
    protected MLModel model;
    protected int layerNum;
    protected int inputDim;
    protected int outputDim;

    protected Matrix output;

    public MLModelLayer(int inputDim, int outputDim){
        if(inputDim <= 0){
            throw new IllegalArgumentException("Input dimension must be > 0");
        }
        if(outputDim <= 0){
            throw new IllegalArgumentException("Output dimension must be > 0");
        }
        this.inputDim = inputDim;
        this.outputDim = outputDim;
    }
    public void setup(MLModel model, int layerNum){
        this.model = model;
        this.layerNum = layerNum;
    }

    /**
     * @return the calculated values (after z (sum) and g (activation func))
     */
    public abstract Matrix forward(Matrix prevLayerInput);

    /**
     * @return error calculated from this layer
     */
    public abstract Matrix backward(Matrix nextLayerError);
}
