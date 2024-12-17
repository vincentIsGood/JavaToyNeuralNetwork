package com.vincentcodes.ml.layers.cnn;

import com.vincentcodes.math.ConvolutionOptions;
import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.math.MatrixStacked3D;
import com.vincentcodes.ml.ActivationFunctions;
import com.vincentcodes.ml.layers.MLModelLayer;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

//TODO: not now
public class MLModelConvolve2DLayer extends MLModelLayer {
    protected ConvolutionOptions convolutionOptions;
    protected MatrixStacked3D[] filterWeights;
    protected double[] filterBiases; // one per whole filter
    protected int squareFilterDim;

    public MLModelConvolve2DLayer(int squareFilterDim, int outputChannels) {
        this(squareFilterDim, outputChannels, ConvolutionOptions.builder().build());
    }
    public MLModelConvolve2DLayer(int squareFilterDim, int outputChannels, ConvolutionOptions convolutionOptions) {
        if(squareFilterDim <= 0){
            throw new IllegalArgumentException("Filter dimension must be > 0");
        }
        Objects.requireNonNull(convolutionOptions);
        this.squareFilterDim = squareFilterDim;
        this.convolutionOptions = convolutionOptions;
        filterWeights = new MatrixStacked3D[outputChannels];

        for(int i = 0; i < filterWeights.length; i++){
            Matrix2D[] filters = new Matrix2D[model.getLayer(layerNum-1).output.depth()];
            for (Matrix2D filter : filters)
                filter.randomizeMut();
            filterWeights[i] = MatrixStacked3D.fromMatrix2D(filters);
        }
        filterBiases = Arrays.stream(new double[filterWeights.length]).map(d -> Math.random()).toArray();
    }

    @Override
    public MatrixStacked3D forward(MatrixStacked3D prevLayerInput) {
        return output = prevLayerInput.convolveMultiple(filterWeights).addMut(filterBiases).applyFunctionMut(activationFunction());
    }

    @Override
    public MatrixStacked3D backward(MatrixStacked3D nextLayerError) {
        // stride>1 is identical to a stride = 1 ==> next.dilate(stride-1)
        //Matrix2D error = output.applyFunction(activationFunctionDeriv()).hadamard(nextLayerError.matrices[0]);
        if(convolutionOptions.strideSize > 1){
            nextLayerError = nextLayerError.dilate(convolutionOptions.strideSize-1);
        }
        return nextLayerError;
    }

    protected Function<Double, Double> activationFunction(){
        return ActivationFunctions.relu;
    }
    protected Function<Double, Double> activationFunctionDeriv(){
        return ActivationFunctions.reluDeriv;
    }
}
