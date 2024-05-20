package com.vincentcodes.ml.layers;

import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.ml.ActivationFunctions;

import java.util.function.Function;

/**
 * @link https://dustinstansbury.github.io/theclevermachine/derivation-backpropagation
 */
public class MLModelFullyConnected extends MLModelLayer {
    protected int inputDim;
    protected int outputDim;
    protected Matrix2D currentWeight;
    protected Matrix2D biasWeight;

    public MLModelFullyConnected(int inputDim, int outputDim) {
        if(inputDim <= 0){
            throw new IllegalArgumentException("Input dimension must be > 0");
        }
        if(outputDim <= 0){
            throw new IllegalArgumentException("Output dimension must be > 0");
        }
        this.inputDim = inputDim;
        this.outputDim = outputDim;
        currentWeight = new Matrix2D(outputDim, inputDim).randomizeMut();
        biasWeight = new Matrix2D(outputDim, 1).randomizeMut();
    }

    public Matrix2D forward(Matrix2D prevLayerInput){
        return output = currentWeight.dotFlexible(prevLayerInput).addMut(biasWeight)
                .applyFunctionMut(activationFunction());
    }

    public Matrix2D backward(Matrix2D nextLayerError){
        Matrix2D error = output.applyFunction(activationFunctionDeriv()).hadamard(nextLayerError);
        Matrix2D weightGradient = error
                .dot(model.getLayer(layerNum-1).output.transpose())
                .hadamardMut(model.getLearningRate());
        biasWeight.addMut(error);
        Matrix2D layerError = currentWeight.dotFlexible(error);
        currentWeight.addMut(weightGradient);
        return layerError;
    }

    protected Function<Double, Double> activationFunction(){
        return ActivationFunctions.sigmoid;
    }
    protected Function<Double, Double> activationFunctionDeriv(){
        return ActivationFunctions.sigmoidDeriv;
    }
}
