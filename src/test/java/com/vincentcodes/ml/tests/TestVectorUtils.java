package com.vincentcodes.ml.tests;

import com.vincentcodes.math.Vector;
import com.vincentcodes.math.VectorUtils;
import org.junit.jupiter.api.Test;

public class TestVectorUtils {
    @Test
    public void test_clipping(){
        Vector vector = Vector.fromArray(new double[]{1,2,3,4,5});
        System.out.println(VectorUtils.clipByNorm(vector, 2));
    }
}
