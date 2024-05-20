package com.vincentcodes.ml.tests;

import com.vincentcodes.math.*;
import org.junit.jupiter.api.Test;

public class TestMathUtils {
    private Matrix2D e = Matrix2D.from2DArray(new double[][]{{1,2}, {3,4}});
    private Matrix2D f = Matrix2D.from2DArray(new double[][]{{1,2,3}, {4,5,6}, {7,8,9}});
    private Matrix2D g = Matrix2D.from2DArray(new double[][]{{1, 2, 3}});
    private Matrix2D h = Matrix2D.from2DArray(new double[][]{{1,2,3,4,5}, {6,7,8,9,10}, {11,12,13,14,15}, {16,17,18,19,20}, {21,22,23,24,25}});
    private Matrix2D i = Matrix2D.from2DArray(new double[][]{{1,2,3,4}, {5,6,7,8}, {9,10,11,12}, {13,14,15,16}});
    private Matrix2D m = Matrix2D.from2DArray(new double[][]{{1,2,2,1}, {2,5,1,3}, {2,9,2,3}, {3,3,4,5}});

    @Test
    public void test_clipping(){
        Vector vector = Vector.fromArray(new double[]{1,2,3,4,5});
        System.out.println(VectorUtils.clipByNorm(vector, 2));
    }

    @Test
    public void test_matrix_convolve_basic1(){
        System.out.println(h.convolve(f));
        /*
        [[411.0, 456.0, 501.0], [636.0, 681.0, 726.0], [861.0, 906.0, 951.0]]
         */
        System.out.println(h.convolve(f, ConvolutionOptions.builder().padSize(1).strideSize(2).build()));
        /*
        [[128.0, 241.0, 184.0], [441.0, 681.0, 453.0], [320.0, 457.0, 280.0]]
         */
        System.out.println(h.convolve(e));
        /*
        [[51.0, 61.0, 71.0, 81.0], [101.0, 111.0, 121.0, 131.0], [151.0, 161.0, 171.0, 181.0], [201.0, 211.0, 221.0, 231.0]]
         */
    }

    @Test
    public void test_matrix_pooling_basic1(){
        System.out.println(m.pooling(2, new PoolingReducer.MaxPooling(), ConvolutionOptions.builder().strideSize(2).build()));
        /*
        [[5,3],[9,5]]
         */
    }

    // ------------ For 3D ------------ //
    // Results should be kinda the same as the above
    @Test
    public void test_matrix3D_convolution(){
        MatrixStacked3D a = MatrixStacked3D.fromMatrix2D(new Matrix2D[]{h, h, h});
        MatrixStacked3D b = MatrixStacked3D.fromMatrix2D(new Matrix2D[]{f, f, f});
        MatrixStacked3D c = MatrixStacked3D.fromMatrix2D(new Matrix2D[]{f, f, f});
        //System.out.println(a.convolve(b).collapseBySum());
        System.out.println(a.convolveMultiple(new MatrixStacked3D[]{b, c}));
    }

    @Test
    public void test_matrix3D_pooling(){
        MatrixStacked3D a = MatrixStacked3D.fromMatrix2D(new Matrix2D[]{m, m, m});
        System.out.println(a.pooling(2, new PoolingReducer.MaxPooling(), ConvolutionOptions.builder().strideSize(2).build()));
    }
}
