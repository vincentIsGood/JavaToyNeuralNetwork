package com.vincentcodes.ml;

import java.util.function.Function;

import com.vincentcodes.math.Matrix;

public class ActivationFunctions{
    public static final Function<Double, Double> sigmoid = (x)-> 1/(1 + Math.exp(-x));

    public static final Function<Double, Double> sigmoidDeriv = (x)-> x * (1 - x);

    public static final Function<Double, Double> relu = (x)-> x < 0? 0.001*x : x;

    public static final Function<Double, Double> reluDeriv = (x)-> x <= 0? 0.001 : 1d;

    public static class SoftMaxFunction implements Function<Double, Double>{
        public static Function<Double, Double> deriv = (x)-> x * (1 - x);

        public double sum;
        
        public SoftMaxFunction setSumFromMatrix(Matrix matrix){
            sum = matrix.sum((x) -> Math.exp(x));
            return this;
        }

        @Override
        public Double apply(Double x) {
            return Math.exp(x) / sum;
        }
    }
}