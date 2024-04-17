package com.vincentcodes.ml.tests;

import com.vincentcodes.math.Matrix;
import com.vincentcodes.ml.MLModel;
import com.vincentcodes.ml.layers.MLModelFullyConnected;
import com.vincentcodes.ml.layers.MLModelInputLayer;
import com.vincentcodes.ml.layers.MLModelOutputLayer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestXorCase {
    @Test
    public void solvingXorProblem(){
        MLModel model = new MLModel();
        model.setLearningRate(.5);
        model.addLayers(
                new MLModelInputLayer(2),
                new MLModelFullyConnected(2, 4),
                new MLModelFullyConnected(4, 4),
                new MLModelOutputLayer(4, 1)
        );

        double[][] inputs  = {{1.0, 0.0}, {0.0, 1.0}, {1.0, 1.0}, {0.0, 0.0}};
        double[][] xorLabels = {{1}, {1}, {0}, {0}};

        for(int i = 0; i < 1000000; i++){
            model.trainSingle(
                    Matrix.fromVect(inputs[i % 4]),
                    Matrix.fromVect(xorLabels[i % 4]));
            model.setLearningRate(model.getLearningRate()*0.9999999);
        }

        for(double[] input : inputs) {
            System.out.println(input[0] + " xor " + input[1] + " = " + model.forward(Matrix.fromVect(input)));
        }
    }
}
