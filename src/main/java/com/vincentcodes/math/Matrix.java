package com.vincentcodes.math;

// ./zPrograms/0_JavaScript/zOwnLib/Matrix.js

import java.util.Arrays;
import java.util.function.Function;

public class Matrix {
    /**
     * Address the matrix by matrix[row][col] where row is y, col is x.
     */
    public double[][] matrix;
    public int rows;
    public int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new double[rows][cols];

        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                this.matrix[row][col] = 0.0;
            }
        }
    }

    public Matrix randomizeMut() {
        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                this.matrix[row][col] = Math.random();
            }
        }
        return this;
    }

    public Matrix sub(Matrix mat) {
        if (this.rows == mat.rows && this.cols == mat.cols) {
            Matrix res = new Matrix(this.rows, this.cols);
            for(int row = 0; row < this.rows; ++row) {
                for(int col = 0; col < this.cols; ++col) {
                    res.matrix[row][col] = this.matrix[row][col] - mat.matrix[row][col];
                }
            }
            return res;
        } else {
            throw new IllegalArgumentException("Both size don't match: " + dimString() + ", " + mat.dimString());
        }
    }
    public Matrix subMut(Matrix mat) {
        if (this.rows == mat.rows && this.cols == mat.cols) {
            Matrix res = this;
            for(int row = 0; row < this.rows; ++row) {
                for(int col = 0; col < this.cols; ++col) {
                    res.matrix[row][col] = this.matrix[row][col] - mat.matrix[row][col];
                }
            }
            return res;
        } else {
            throw new IllegalArgumentException("Both size don't match: " + dimString() + ", " + mat.dimString());
        }
    }

    public Matrix sub(double mat) {
        Matrix res = new Matrix(this.rows, this.cols);

        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                res.matrix[row][col] = this.matrix[row][col] - mat;
            }
        }

        return res;
    }

    public Matrix add(Matrix mat) {
        if (this.rows == mat.rows && this.cols == mat.cols) {
            Matrix res = new Matrix(this.rows, this.cols);
            for(int row = 0; row < this.rows; ++row) {
                for(int col = 0; col < this.cols; ++col) {
                    res.matrix[row][col] = this.matrix[row][col] + mat.matrix[row][col];
                }
            }

            return res;
        } else {
            throw new IllegalArgumentException("Both size don't match: " + dimString() + ", " + mat.dimString());
        }
    }
    public Matrix addMut(Matrix mat) {
        if (this.rows == mat.rows && this.cols == mat.cols) {
            Matrix res = this;
            for(int row = 0; row < this.rows; ++row) {
                for(int col = 0; col < this.cols; ++col) {
                    res.matrix[row][col] = this.matrix[row][col] + mat.matrix[row][col];
                }
            }

            return this;
        } else {
            throw new IllegalArgumentException("Both size don't match: " + dimString() + ", " + mat.dimString());
        }
    }

    public Matrix add(double num) {
        Matrix res = new Matrix(this.rows, this.cols);

        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                res.matrix[row][col] = this.matrix[row][col] + num;
            }
        }

        return res;
    }

    public Matrix dot(Matrix mat) {
        if (this.cols != mat.rows) {
            throw new IllegalArgumentException("Dot product requirement not met: " + this.dimString() + ", " + mat.dimString());
        }
        Matrix res = new Matrix(this.rows, mat.cols);
        for(int row = 0; row < res.rows; ++row) {
            for(int col = 0; col < res.cols; ++col) {
                double sum = 0.0;

                for(int i = 0; i < this.cols; ++i) {
                    sum += this.matrix[row][i] * mat.matrix[i][col];
                }

                res.matrix[row][col] = sum;
            }
        }
        return res;
    }

    public Matrix dotFlexible(Matrix mat) {
        if(this.cols != mat.rows && this.rows == mat.rows){
            return transpose().dot(mat);
        }
        return dot(mat);
    }
    public Matrix dotParamFlexible(Matrix mat) {
        if(this.cols != mat.rows && (this.cols == mat.cols)){
            return dot(mat.transpose());
        }
        return dot(mat);
    }

    // element-wise multiplication
    public Matrix hadamard(Matrix mat) {
        if (this.rows == mat.rows && this.cols == mat.cols) {
            Matrix res = new Matrix(this.rows, this.cols);

            for(int row = 0; row < this.rows; ++row) {
                for(int col = 0; col < this.cols; ++col) {
                    res.matrix[row][col] = this.matrix[row][col] * mat.matrix[row][col];
                }
            }

            return res;
        } else {
            throw new IllegalArgumentException("Both size don't match: " + dimString() + ", " + mat.dimString());
        }
    }
    public Matrix hadamardMut(Matrix mat) {
        if (this.rows == mat.rows && this.cols == mat.cols) {
            Matrix res = this;

            for(int row = 0; row < this.rows; ++row) {
                for(int col = 0; col < this.cols; ++col) {
                    res.matrix[row][col] = this.matrix[row][col] * mat.matrix[row][col];
                }
            }

            return res;
        } else {
            throw new IllegalArgumentException("Both size don't match: " + dimString() + ", " + mat.dimString());
        }
    }

    public Matrix hadamard(double scalar) {
        Matrix res = new Matrix(this.rows, this.cols);

        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                res.matrix[row][col] = this.matrix[row][col] * scalar;
            }
        }

        return res;
    }
    public Matrix hadamardMut(double scalar) {
        Matrix res = this;

        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                res.matrix[row][col] = this.matrix[row][col] * scalar;
            }
        }

        return res;
    }

    /**
     * Convolute a matrix with another.
     *
     * Inspired by 3Blue1Brown's animation:
     * https://www.youtube.com/watch?v=KuXjwB4LzSA
     *
     * For even-sized kernel, the solution is as follows:
     * https://stats.stackexchange.com/questions/366739/when-do-we-use-an-even-size-kernel-in-convolutional-neural-network-and-why
     *
     * @link https://medium.com/@nghihuynh_37300/convolutional-neural-networks-for-image-recognition-7148a19f981f
     */
    public Matrix convolve(Matrix kernel){
        return convolve(kernel, null);
    }
    public Matrix convolve(Matrix kernel, ConvolutionOptions options){
        if(kernel.cols != kernel.rows){
            throw new IllegalArgumentException("Kernel is not a square matrix");
        }

        if(options == null) options = new ConvolutionOptions();
        boolean flip = options.flip;
        int padSize = options.padSize;
        int strideSize = options.strideSize;

        if(strideSize <= 0){
            throw new IllegalArgumentException("Stride size must be larger than 0");
        }
        
        if(flip){
            kernel = kernel.flip180();
        }

        // Ouput size = floor((input size + 2 * padding size - kernel size) / stride) +1
        int finalMatrixColSize = (int)Math.floor((this.cols + 2 * padSize - kernel.cols) / (double)strideSize) +1;
        int finalMatrixRowSize = (int)Math.floor((this.rows + 2 * padSize - kernel.rows) / (double)strideSize) +1;
        int kernelMidPointCol = (int)Math.floor(kernel.cols/2.0);
        int kernelMidPointRow = (int)Math.floor(kernel.rows/2.0);

        int even = 0;
        if((kernel.cols & 1) == 0){
            even = 1;
        }

        Matrix paddedMatrix = padSize > 0? this.padMatrix(padSize) : this;
        Matrix resultMatrix = new Matrix(finalMatrixColSize, finalMatrixRowSize);
        for(int i = kernelMidPointRow, resI = 0; i < paddedMatrix.rows - kernelMidPointRow + even; i+=strideSize, resI++){
            for(int j = kernelMidPointCol, resJ = 0; j < paddedMatrix.cols - kernelMidPointCol + even; j+=strideSize, resJ++){
                double weightedSum = 0;
                for(int y = 0; y < kernel.rows; y++){
                    for(int x = 0; x < kernel.cols; x++){
                        weightedSum += kernel.matrix[y][x] * paddedMatrix.matrix[i + y - kernelMidPointRow][j + x - kernelMidPointCol];
                    }
                }
                resultMatrix.matrix[resI][resJ] = weightedSum;
            }
        }
        return resultMatrix;
    }

    /**
     * Do not do convolution in this function.
     * @param {number} filterSize length of an edge of a square filter
     * @param {Reducer} reducer
     * @param {ConvolutionOptions} options
     */
    public Matrix pooling(int filterSize, PoolingReducer reducer, ConvolutionOptions options){
        if(reducer == null){
            throw new Error("Reducer is required");
        }

        if(options == null) options = new ConvolutionOptions();
        int padSize = options.padSize;
        int strideSize = options.strideSize;

        if(strideSize <= 0){
            throw new IllegalArgumentException("Stride size must be larger than 0");
        }

        // Ouput size = floor((input size + 2 * padding size - kernel size) / stride) +1
        int finalMatrixColSize = (int)Math.floor((this.cols + 2 * padSize - filterSize) / (double)strideSize) +1;
        int finalMatrixRowSize = (int)Math.floor((this.rows + 2 * padSize - filterSize) / (double)strideSize) +1;
        int kernelMidPointCol = (int)Math.floor(filterSize/2.0);
        int kernelMidPointRow = (int)Math.floor(filterSize/2.0);

        int even = 0;
        if((filterSize & 1) == 0){
            even = 1;
        }

        Matrix paddedMatrix = padSize > 0? this.padMatrix(padSize) : this;
        Matrix resultMatrix = new Matrix(finalMatrixColSize, finalMatrixRowSize);
        for(int i = kernelMidPointRow, resI = 0; i < paddedMatrix.rows - kernelMidPointRow + even; i+=strideSize, resI++){
            for(int j = kernelMidPointCol, resJ = 0; j < paddedMatrix.cols - kernelMidPointCol + even; j+=strideSize, resJ++){
                reducer.init(filterSize*filterSize);
                for(int y = 0; y < filterSize; y++){
                    for(int x = 0; x < filterSize; x++){
                        reducer.compute(paddedMatrix.matrix[i + y - kernelMidPointRow][j + x - kernelMidPointCol]);
                    }
                }
                resultMatrix.matrix[resI][resJ] = reducer.result();
            }
        }
        return resultMatrix;
    }

    public Matrix transpose() {
        Matrix res = new Matrix(this.cols, this.rows);

        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                res.matrix[col][row] = this.matrix[row][col];
            }
        }

        return res;
    }

    /**
     * [[1,2],     [[4,3],
     *  [3,4]] -->  [2,1]]
     * 
     * [[1,2,3],     [[9,8,7],
     *  [4,5,6], -->  [6,5,4],
     *  [7,8,9]]      [3,2,1]]
     */
    public Matrix flip180(){
        Matrix resultMatrix = new Matrix(this.rows, this.cols);
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                resultMatrix.matrix[i][j] = this.matrix[this.rows-1-i][this.cols-1-j];
            }
        }
        return resultMatrix;
    }
    
    /**
     * [[0]] by size 1 => [[0 0 0],
     *                     [0 0 0],
     *                     [0 0 0]]
     * @param {number} padSize 
     */
    public Matrix padMatrix(int padSize){
        Matrix resultMatrix = new Matrix(this.rows + padSize*2, this.cols + padSize*2);
        int currentRowSize = padSize;
        int currentColSize = padSize;
        for(int i = 0; i < resultMatrix.rows; i++){
            if(i > padSize+this.rows-1){
                currentRowSize = padSize;
            }
            currentColSize = padSize;

            for(int j = 0; j < resultMatrix.cols; j++){
                if(j > padSize+this.cols-1){
                    currentColSize = padSize;
                }

                if(currentRowSize > 0 || currentColSize > 0){
                    resultMatrix.matrix[i][j] = 0;
                }else resultMatrix.matrix[i][j] = this.matrix[i-padSize][j-padSize];
                currentColSize--;
            }
            currentRowSize--;
        }
        return resultMatrix;
    }

    // apply function in an element-wise manner
    public Matrix applyFunction(Function<Double, Double> func) {
        Matrix res = new Matrix(this.rows, this.cols);

        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                res.matrix[row][col] = func.apply(this.matrix[row][col]);
            }
        }

        return res;
    }
    public Matrix applyFunctionMut(Function<Double, Double> func) {
        Matrix res = this;

        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                res.matrix[row][col] = func.apply(this.matrix[row][col]);
            }
        }

        return res;
    }

    public double sum(Function<Double, Double> func){
        double sum = 0;
        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                sum += func.apply(this.matrix[row][col]);
            }
        }
        return sum;
    }

    public void copyFrom(Matrix mat) {
        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                this.matrix[row][col] = mat.matrix[row][col];
            }
        }
    }

    public Vector toVector(){
        if(cols != 1){
            throw new IllegalStateException("A vector requires 1 column matrix. But you have: " + dimString());
        }
        Vector res = new Vector(rows);
        for(int i = 0; i < rows; i++){
            res.vect[i] = matrix[i][0];
        }
        return res;
    }

    public String dimString(){
        return "(" + this.rows + ", " + this.cols + ")";
    }

    public String toString() {
        return Arrays.deepToString(matrix);
    }

    // ----------------- Static ----------------- //
    public static double[][] to2DArray(Matrix mat) {
        double[][] var1 = new double[mat.rows][mat.cols];

        for(int row = 0; row < mat.rows; ++row) {
            for(int col = 0; col < mat.cols; ++col) {
                var1[row][col] = mat.matrix[row][col];
            }
        }

        return var1;
    }

    public static Matrix from2DArray(double[][] mat) {
        Matrix res = new Matrix(mat.length, mat[0].length);

        for(int row = 0; row < res.rows; ++row) {
            for(int col = 0; col < res.cols; ++col) {
                res.matrix[row][col] = mat[row][col];
            }
        }

        return res;
    }

    public static Matrix fromVect(double[] vect) {
        Matrix res = new Matrix(vect.length, 1);

        for(int row = 0; row < res.rows; ++row) {
            res.matrix[row][0] = vect[row];
        }

        return res;
    }
}
