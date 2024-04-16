package com.vincentcodes.ml.layers;

import com.vincentcodes.math.Matrix;

/**
 * @link https://dustinstansbury.github.io/theclevermachine/derivation-backpropagation
 */
public class MLModelOutputLayer extends MLModelFullyConnected {

    public MLModelOutputLayer(int inputDim, int outputDim) {
        super(inputDim, outputDim);
    }

    /**
     * @return error calculated from this layer
     */
    @Override
    public Matrix backward(Matrix label){
        // dg/dz * (y - label) * x
        // (x = dz/dw comes from z = sum(x*w) which comes from previous layer)
        //
        // define error = dg/dz * (y - label)
        //
        // why hadamard (element-wise) multiplication? Because errors should propagate in a per-weight manner
        Matrix error = output.applyFunction(activationFunctionDeriv()).hadamard(label.sub(output));
        Matrix gradient = error
                .dot(model.getLayer(layerNum-1).output.transpose())
                .hadamardMut(model.getLearningRate());
        biasWeight.addMut(error);
        Matrix layerError = currentWeight.dotFlexible(error);
        currentWeight.addMut(gradient);
        return layerError;
    }
}
