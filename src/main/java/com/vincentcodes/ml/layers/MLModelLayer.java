package com.vincentcodes.ml.layers;

import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.ml.MLModel;

public abstract class MLModelLayer {
    protected MLModel model;
    protected int layerNum;

    protected Matrix2D output;

    public void setup(MLModel model, int layerNum){
        this.model = model;
        this.layerNum = layerNum;
    }

    /**
     * @return the calculated values (after z (sum) and g (activation func))
     */
    public abstract Matrix2D forward(Matrix2D prevLayerInput);

    /**
     * @return error calculated from this layer
     */
    public abstract Matrix2D backward(Matrix2D nextLayerError);
}
