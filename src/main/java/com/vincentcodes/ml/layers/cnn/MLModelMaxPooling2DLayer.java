package com.vincentcodes.ml.layers.cnn;

import com.vincentcodes.math.ConvolutionOptions;
import com.vincentcodes.math.MatrixStacked3D;
import com.vincentcodes.math.PoolingReducer;
import com.vincentcodes.ml.layers.MLModelLayer;

import java.util.Objects;

//TODO: not now
public class MLModelMaxPooling2DLayer extends MLModelLayer {
    protected ConvolutionOptions convolutionOptions;
    protected int maxPoolingFilterDim;

    public MLModelMaxPooling2DLayer(int maxPoolingFilterDim) {
        this(maxPoolingFilterDim, ConvolutionOptions.builder().build());
    }
    public MLModelMaxPooling2DLayer(int maxPoolingFilterDim, ConvolutionOptions convolutionOptions) {
        if(maxPoolingFilterDim <= 0){
            throw new IllegalArgumentException("Filter dimension must be > 0");
        }
        Objects.requireNonNull(convolutionOptions);
        this.maxPoolingFilterDim = maxPoolingFilterDim;
        this.convolutionOptions = convolutionOptions;
    }

    @Override
    public MatrixStacked3D forward(MatrixStacked3D prevLayerInput) {
        return output = prevLayerInput.pooling(maxPoolingFilterDim, new PoolingReducer.MaxPooling(), convolutionOptions);
    }

    @Override
    public MatrixStacked3D backward(MatrixStacked3D nextLayerError) {
        return nextLayerError;
    }
}
