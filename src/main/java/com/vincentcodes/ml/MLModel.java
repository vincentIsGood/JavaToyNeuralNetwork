package com.vincentcodes.ml;

import com.vincentcodes.math.Matrix2D;
import com.vincentcodes.math.MatrixStacked3D;
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

    public Matrix2D forward(Matrix2D input){
        MatrixStacked3D output = MatrixStacked3D.fromMatrix2D(input);
        for(MLModelLayer layer : layers){
            output = layer.forward(output);
        }
        return output.matrices[0];
    }
    public MatrixStacked3D forward(MatrixStacked3D input){
        MatrixStacked3D output = input;
        for(MLModelLayer layer : layers){
            output = layer.forward(output);
        }
        return output;
    }

    public void trainSingle(Matrix2D input, Matrix2D label){
        forward(input);
        MatrixStacked3D error = MatrixStacked3D.fromMatrix2D(label);
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
