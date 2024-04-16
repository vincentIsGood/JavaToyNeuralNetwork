package com.vincentcodes.ml;

import com.vincentcodes.math.Matrix;
import com.vincentcodes.ml.layers.MLModelLayer;

import java.util.ArrayList;
import java.util.List;

public class MLModel {
    private final List<MLModelLayer> layers = new ArrayList<>();
    private double learningRate = 0.05;

    public void addLayer(MLModelLayer layer){
        layer.setup(this, layers.size());
        layers.add(layer);
    }
    public void addLayers(MLModelLayer... layers){
        for(MLModelLayer layer : layers){
            addLayer(layer);
        }
    }

    public Matrix forward(Matrix input){
        Matrix output = input;
        for(MLModelLayer layer : layers){
            output = layer.forward(output);
        }
        return output;
    }

    public void trainSingle(Matrix input, Matrix label){
        forward(input);
        Matrix error = label;
        for(int i = layers.size()-1; i >= 0; i--){
            MLModelLayer layer = layers.get(i);
            error = layer.backward(error);
        }
    }

    public MLModelLayer getLayer(int i){
        return layers.get(i);
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }
}
