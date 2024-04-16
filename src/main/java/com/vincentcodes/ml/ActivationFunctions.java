package com.vincentcodes.ml;

import java.util.function.Function;

public class ActivationFunctions{
    public static final Function<Double, Double> sigmoid = (x)-> 1/(1 + Math.exp(-x));

    public static final Function<Double, Double> sigmoidDeriv = (x)-> x * (1 - x);

    public static final Function<Double, Double> relu = (x)-> x < 0? 0d : x;

    public static final Function<Double, Double> reluDeriv = (x)-> x <= 0? 0d : 1d;
}
