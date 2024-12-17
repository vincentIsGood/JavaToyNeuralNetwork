package com.vincentcodes.ml.layers;

import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.math.MatrixStacked3D;

/**
 * Requires previous layer to be a fully connected layer
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
}
