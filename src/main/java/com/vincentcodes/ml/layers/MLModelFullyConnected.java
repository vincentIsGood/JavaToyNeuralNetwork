package com.vincentcodes.ml.layers;

import com.vincentcodes.math.Matrix;
import com.vincentcodes.ml.ActivationFunctions;

import java.util.function.Function;

/**
 * @link https://dustinstansbury.github.io/theclevermachine/derivation-backpropagation
 */
public class MLModelFullyConnected extends MLModelLayer {
    protected Matrix currentWeight;
    protected Matrix biasWeight;

    public MLModelFullyConnected(int inputDim, int outputDim) {
        super(inputDim, outputDim);
        currentWeight = new Matrix(outputDim, inputDim).randomizeMut();
        biasWeight = new Matrix(outputDim, 1).randomizeMut();
    }

    public Matrix forward(Matrix prevLayerInput){
        return output = currentWeight.dotFlexible(prevLayerInput).addMut(biasWeight)
                .applyFunctionMut(activationFunction());
    }

    public Matrix backward(Matrix nextLayerError){
        Matrix error = output.applyFunction(activationFunctionDeriv()).hadamard(nextLayerError);
        Matrix weightGradient = error
                .dot(model.getLayer(layerNum-1).output.transpose())
                .hadamardMut(model.getLearningRate());
        biasWeight.addMut(error);
        Matrix layerError = currentWeight.dotFlexible(error);
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
