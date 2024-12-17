package com.vincentcodes.ml.layers;

import java.util.function.Function;

import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.math.MatrixStacked3D;
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
    public MatrixStacked3D forward(MatrixStacked3D prevLayerInput){
        output = currentWeight.dotFlexible(prevLayerInput).addMut(biasWeight);
        softMaxFunction.setSumFromMatrix(output.matrices[0]);
        return output.applyFunctionMut(activationFunction());
    }

    /**
     * @return error calculated from this layer
     */
    @Override
    public MatrixStacked3D backward(MatrixStacked3D label){
        // dg/dz * (y - label) * x
        // (z = sum(x*w) => dz/dw = x  which comes from previous layer)
        //
        // define error = dg/dz * (y - label)
        //
        // why hadamard (element-wise) multiplication? Because errors should propagate in a per-weight manner
        Matrix2D outputMat = output.matrices[0];
        Matrix2D labelMat = label.matrices[0];
        Matrix2D currentWeightMat = currentWeight.matrices[0];

        Matrix2D error = outputMat.applyFunction(activationFunctionDeriv()).hadamard(labelMat.sub(outputMat));
        Matrix2D gradient = error
                .dot(model.getLayer(layerNum-1).output.matrices[0].transpose())
                .hadamardMut(model.getLearningRate());
        biasWeight.addMut(error);
        Matrix2D layerError = currentWeightMat.dotFlexible(error);
        currentWeightMat.addMut(gradient);
        return MatrixStacked3D.fromMatrix2D(layerError);
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
