package com.vincentcodes.math;

public class VectorUtils {
    /**
     * @link https://neptune.ai/blog/understanding-gradient-clipping-and-how-it-can-fix-exploding-gradients-problem
     */
    public static Vector clipByNorm(Vector vect, double threshold){
        return vect.normalized().multiply(threshold);
    }
}
