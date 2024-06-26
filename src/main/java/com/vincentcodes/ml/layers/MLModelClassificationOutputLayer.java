package com.vincentcodes.ml.layers;

import java.util.function.Function;

import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.ml.ActivationFunctions;
import com.vincentcodes.ml.ActivationFunctions.SoftMaxFunction;

/**
 * @link https://dustinstansbury.github.io/theclevermachine/derivation-backpropagation
 */
public class MLModelClassificationOutputLayer extends MLModelFullyConnected {
    private SoftMaxFunction softMaxFunction = new ActivationFunctions.SoftMaxFunction();

    public MLModelClassificationOutputLayer(int inputDim, int outputDim) {
        super(inputDim, outputDim);
    }

    @Override
    public Matrix2D forward(Matrix2D prevLayerInput){
        output = currentWeight.dotFlexible(prevLayerInput).addMut(biasWeight);
        softMaxFunction.setSumFromMatrix(output);
        return output.applyFunctionMut(activationFunction());
    }

    /**
     * @return error calculated from this layer
     */
    @Override
    public Matrix2D backward(Matrix2D label){
        // dg/dz * (y - label) * x
        // (x = dz/dw comes from z = sum(x*w) which comes from previous layer)
        //
        // define error = dg/dz * (y - label)
        //
        // why hadamard (element-wise) multiplication? Because errors should propagate in a per-weight manner
        Matrix2D error = output.applyFunction(activationFunctionDeriv()).hadamard(label.sub(output));
        Matrix2D gradient = error
                .dot(model.getLayer(layerNum-1).output.transpose())
                .hadamardMut(model.getLearningRate());
        biasWeight.addMut(error);
        Matrix2D layerError = currentWeight.dotFlexible(error);
        currentWeight.addMut(gradient);
        return layerError;
    }
    
    @Override
    protected Function<Double, Double> activationFunction(){
        return softMaxFunction;
    }
    @Override
    protected Function<Double, Double> activationFunctionDeriv(){
        return SoftMaxFunction.deriv;
    }
}
