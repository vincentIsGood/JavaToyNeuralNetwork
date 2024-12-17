package com.vincentcodes.ml.layers;

import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.math.MatrixStacked3D;
import com.vincentcodes.ml.ActivationFunctions;

import java.util.function.Function;

/**
 * Requires previous layer to be an adapter layer to convert stuff back into 1D for fully connected
 * @link https://dustinstansbury.github.io/theclevermachine/derivation-backpropagation
 */
public class MLModelFullyConnected extends MLModelLayer {
    protected int inputDim;
    protected int outputDim;
    protected MatrixStacked3D currentWeight;
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
        currentWeight = MatrixStacked3D.fromMatrix2D(new Matrix2D(outputDim, inputDim).randomizeMut());
        biasWeight = new Matrix2D(outputDim, 1).randomizeMut();
    }

    public MatrixStacked3D forward(MatrixStacked3D prevLayerInput){
        return output = currentWeight.dotFlexible(prevLayerInput).addMut(biasWeight)
                .applyFunctionMut(activationFunction());
    }

    public MatrixStacked3D backward(MatrixStacked3D nextLayerError){
        Matrix2D outputMat = output.matrices[0];
        Matrix2D currentWeightMat = currentWeight.matrices[0];

        Matrix2D error = outputMat.applyFunction(activationFunctionDeriv()).hadamard(nextLayerError.matrices[0]);
        Matrix2D weightGradient = error
                .dot(model.getLayer(layerNum-1).output.matrices[0].transpose())
                .hadamardMut(model.getLearningRate());
        biasWeight.addMut(error);
        Matrix2D layerError = currentWeightMat.dotFlexible(error);
        currentWeightMat.addMut(weightGradient);
        return MatrixStacked3D.fromMatrix2D(layerError);
    }

    protected Function<Double, Double> activationFunction(){
        return ActivationFunctions.sigmoid;
    }
    protected Function<Double, Double> activationFunctionDeriv(){
        return ActivationFunctions.sigmoidDeriv;
    }
}
