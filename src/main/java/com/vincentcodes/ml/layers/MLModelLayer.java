package com.vincentcodes.ml.layers;

import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.math.MatrixStacked3D;
import com.vincentcodes.ml.MLModel;

public abstract class MLModelLayer {
    public MLModel model;
    public int layerNum;

    public MatrixStacked3D output;

    public void setup(MLModel model, int layerNum){
        this.model = model;
        this.layerNum = layerNum;
    }

    /**
     * @return the calculated values (after z (sum) and g (activation func))
     */
    public abstract MatrixStacked3D forward(MatrixStacked3D prevLayerInput);

    /**
     * @return error calculated from this layer
     */
    public abstract MatrixStacked3D backward(MatrixStacked3D nextLayerError);
}
