package com.vincentcodes.math;

import java.util.Arrays;

/**
 * Stacks Matrix2Ds of the same dimension to create Matrix3D
 */
public class MatrixStacked3D {
    public Matrix2D[] matrices;
    public final int rows;
    public final int cols;

    private MatrixStacked3D(int rows, int cols, int depth){
        this.rows = rows;
        this.cols = cols;
        this.matrices = new Matrix2D[depth];
    }
    public MatrixStacked3D(Matrix2D[] matrices){
        this.matrices = matrices;

        if(matrices.length == 0)
            throw new IllegalArgumentException("Cannot create empty MatrixStacked3D");

        rows = matrices[0].rows;
        cols = matrices[1].cols;
        for(Matrix2D mat : matrices){
            if(mat.rows != rows || mat.cols != cols)
                throw new IllegalArgumentException("Not all matrices are the same in size");
        }
    }
    public static MatrixStacked3D fromMatrix2D(Matrix2D[] matrices){
        return new MatrixStacked3D(matrices);
    }

    public MatrixStacked3D addMut(double n){
        for(Matrix2D matrix2D : matrices){
            matrix2D.addMut(n);
        }
        return this;
    }

    public MatrixStacked3D convolve(MatrixStacked3D kernel3D){
        return convolve(kernel3D, null);
    }
    public MatrixStacked3D convolve(MatrixStacked3D kernel3D, ConvolutionOptions options){
        int depth = depth();
        if(depth != kernel3D.depth()){
            throw new IllegalArgumentException("Convolving 2 MatrixStacked3D with different depths");
        }
        if(options == null) options = new ConvolutionOptions();

        // Ouput size = floor((input size + 2 * padding size - kernel size) / stride) +1
        int finalMatrixRowSize = (int)Math.floor((this.rows + 2 * options.padSize - kernel3D.rows) / (double)options.strideSize) +1;
        int finalMatrixColSize = (int)Math.floor((this.cols + 2 * options.padSize - kernel3D.cols) / (double)options.strideSize) +1;

        MatrixStacked3D resultMatrices = new MatrixStacked3D(finalMatrixRowSize, finalMatrixColSize, depth);
        for(int c = 0; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].convolve(kernel3D.matrices[c], options);
        }
        return resultMatrices;
    }

    /**
     * Convolve multiple kernels channel-wise and collapse them by addition
     */
    public MatrixStacked3D convolveMultiple(MatrixStacked3D[] kernel3D){
        return convolveMultiple(kernel3D, null);
    }
    public MatrixStacked3D convolveMultiple(MatrixStacked3D[] kernel3D, ConvolutionOptions options){
        if(kernel3D.length == 0){
            throw new IllegalArgumentException("No kernels to convolve with");
        }
        if(options == null) options = new ConvolutionOptions();

        // Ouput size = floor((input size + 2 * padding size - kernel size) / stride) +1
        int finalMatrixRowSize = (int)Math.floor((this.rows + 2 * options.padSize - kernel3D[0].rows) / (double)options.strideSize) +1;
        int finalMatrixColSize = (int)Math.floor((this.cols + 2 * options.padSize - kernel3D[0].cols) / (double)options.strideSize) +1;

        MatrixStacked3D resultMatrices = new MatrixStacked3D(finalMatrixRowSize, finalMatrixColSize, kernel3D.length);
        for(int c = 0; c < resultMatrices.matrices.length; c++){
            resultMatrices.matrices[c] = this.convolve(kernel3D[c], options).collapseBySum();
        }
        return resultMatrices;
    }

    public MatrixStacked3D pooling(int filterSize, PoolingReducer reducer, ConvolutionOptions options){
        int depth = depth();
        if(options == null) options = new ConvolutionOptions();

        // Ouput size = floor((input size + 2 * padding size - kernel size) / stride) +1
        int finalMatrixRowSize = (int)Math.floor((this.rows + 2 * options.padSize - filterSize) / (double)options.strideSize) +1;
        int finalMatrixColSize = (int)Math.floor((this.cols + 2 * options.padSize - filterSize) / (double)options.strideSize) +1;

        MatrixStacked3D resultMatrices = new MatrixStacked3D(finalMatrixRowSize, finalMatrixColSize, depth);
        for(int c = 0; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].pooling(filterSize, reducer, options);
        }
        return resultMatrices;
    }

    public Matrix2D collapseBySum(){
        Matrix2D resultMatrix = new Matrix2D(rows, cols);
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double sum = 0;
                for(int c = 0; c < matrices.length; c++){
                    sum += matrices[c].matrix[i][j];
                }
                resultMatrix.matrix[i][j] = sum;
            }
        }
        return resultMatrix;
    }

    public int depth(){
        return matrices.length;
    }

    public String dimString(){
        return "(" + this.rows + ", " + this.cols + ", " + depth() + ")";
    }

    public String toString() {
        return Arrays.deepToString(matrices);
    }
}
