package com.vincentcodes.ml.tests;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.ml.MLModel;
import com.vincentcodes.ml.layers.MLModelClassificationOutputLayer;
import com.vincentcodes.ml.layers.MLModelFullyConnected;
import com.vincentcodes.ml.layers.MLModelInputLayer;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestClassificationCase {
    /**
     * Case:
     * 2 outputs: xor output is 0, is 1
     */
    @Test
    public void solvingXorProblem(){
        MLModel model = new MLModel();
        model.setLearningRate(.5);
        model.addLayers(
                new MLModelInputLayer(2),
                new MLModelFullyConnected(2, 4),
                new MLModelFullyConnected(4, 4),
                new MLModelClassificationOutputLayer(4, 2)
        );

        double[][] inputs  = {{1.0, 0.0}, {0.0, 1.0}, {1.0, 1.0}, {0.0, 0.0}};
        double[][] labels = {{0, 1}, {0, 1}, {1, 0}, {1, 0}};

        for(int i = 0; i < 1000000; i++){
            model.trainSingle(
                    Matrix2D.fromVect(inputs[i % inputs.length]),
                    Matrix2D.fromVect(labels[i % inputs.length]));
            model.setLearningRate(model.getLearningRate()*0.9999999);
        }

        for(double[] input : inputs) {
            System.out.println(Arrays.toString(input) + " => " + model.forward(Matrix2D.fromVect(input)));
        }
    }

    /**
     * Case:
     * 3 inputs: [use ecommerce, use cash, use online payment]
     * 2 outputs: [youngster, elderly]
     */
    @Test
    public void solvingStupidProblem(){
        MLModel model = new MLModel();
        model.setLearningRate(.5);
        model.addLayers(
                new MLModelInputLayer(3),
                new MLModelFullyConnected(3, 4),
                new MLModelFullyConnected(4, 4),
                new MLModelClassificationOutputLayer(4, 2)
        );

        double[][] inputs  = {
            {1, 0, 1}, 
            {1, 0, 0}, 
            {0, 0, 1}, 
            {1, 1, 1}, 
            {0, 1, 0}
        };
        double[][] labels = {
            {1, 0}, 
            {1, 0}, 
            {1, 0}, 
            {1, 0},
            {0, 1} 
        };

        for(int i = 0; i < 1000000; i++){
            model.trainSingle(
                    Matrix2D.fromVect(inputs[i % inputs.length]),
                    Matrix2D.fromVect(labels[i % inputs.length]));
            model.setLearningRate(model.getLearningRate()*0.9999999);
        }

        System.out.println("[use ecommerce, use cash, use online payment] => [youngster, elderly]");
        for(double[] input : inputs) {
            System.out.println(Arrays.toString(input) + " => " + model.forward(Matrix2D.fromVect(input)));
        }

        for(double[] input : new double[][]{
            {1, 1, 0}, {0, 1, 1}
        }) {
            System.out.println(Arrays.toString(input) + " => " + model.forward(Matrix2D.fromVect(input)));
        }
    }
}
