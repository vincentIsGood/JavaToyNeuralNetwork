package com.vincentcodes.ml.layers.cnn;

import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.math.MatrixStacked3D;
import com.vincentcodes.ml.layers.MLModelLayer;

import java.util.Arrays;

//TODO: not now
public class MLModelInput2DLayer extends MLModelLayer {

    @Override
    public MatrixStacked3D forward(MatrixStacked3D prevLayerInput){
        return output = prevLayerInput;
    }

    @Override
    public MatrixStacked3D backward(MatrixStacked3D nextLayerError) {
        return null;
    }
}
