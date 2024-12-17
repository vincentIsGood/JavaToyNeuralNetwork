package com.vincentcodes.math;

import java.util.Arrays;
import java.util.function.Function;

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
        cols = matrices[0].cols;
        for(Matrix2D mat : matrices){
            if(mat.rows != rows || mat.cols != cols)
                throw new IllegalArgumentException("Not all matrices are the same in size");
        }
    }
    public static MatrixStacked3D fromMatrix2D(Matrix2D[] matrices){
        return new MatrixStacked3D(matrices);
    }
    public static MatrixStacked3D fromMatrix2D(Matrix2D matrix){
        return new MatrixStacked3D(new Matrix2D[]{matrix});
    }

    // ------------- Matrix2D stuff on 3D (mostly channel-wise ops) ------------- //
    public MatrixStacked3D add(Matrix2D n){
        int depth = depth();

        Matrix2D firstResult = this.matrices[0].add(n);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].add(n);
        }
        return resultMatrices;
    }
    public MatrixStacked3D add(MatrixStacked3D n){
        int depth = depth();

        Matrix2D firstResult = this.matrices[0].add(n.matrices[0]);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].add(n.matrices[0]);
        }
        return resultMatrices;
    }
    public MatrixStacked3D addMut(double n){
        for(Matrix2D matrix2D : matrices){
            matrix2D.addMut(n);
        }
        return this;
    }
    public MatrixStacked3D addMut(double[] n){
        if(matrices.length != n.length)
            throw new IllegalArgumentException("Depths do not match");
        for (int i = 0; i < matrices.length; i++) {
            Matrix2D matrix2D = matrices[i];
            matrix2D.addMut(n[i]);
        }
        return this;
    }
    public MatrixStacked3D addMut(Matrix2D n){
        for(Matrix2D matrix2D : matrices){
            matrix2D.addMut(n);
        }
        return this;
    }

    public MatrixStacked3D sub(Matrix2D n){
        int depth = depth();

        Matrix2D firstResult = this.matrices[0].sub(n);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].sub(n);
        }
        return resultMatrices;
    }
    public MatrixStacked3D sub(MatrixStacked3D n){
        int depth = depth();

        Matrix2D firstResult = this.matrices[0].sub(n.matrices[0]);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].sub(n.matrices[0]);
        }
        return resultMatrices;
    }
    public MatrixStacked3D subMut(double n){
        for(Matrix2D matrix2D : matrices){
            matrix2D.subMut(n);
        }
        return this;
    }
    public MatrixStacked3D subMut(Matrix2D n){
        for(Matrix2D matrix2D : matrices){
            matrix2D.subMut(n);
        }
        return this;
    }

    public MatrixStacked3D hadamard(double n) {
        int depth = depth();
        Matrix2D firstResult = this.matrices[0].hadamard(n);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].hadamard(n);
        }
        return resultMatrices;
    }
    public MatrixStacked3D hadamard(MatrixStacked3D mat) {
        int depth = depth();
        if(depth != mat.depth()){
            throw new IllegalArgumentException("Dotting 2 MatrixStacked3D with different depths");
        }

        Matrix2D firstResult = this.matrices[0].hadamard(mat.matrices[0]);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].hadamard(mat.matrices[c]);
        }
        return resultMatrices;
    }
    public MatrixStacked3D hadamardMut(double n) {
        int depth = depth();
        for(int c = 0; c < depth; c++){
            this.matrices[c].hadamardMut(n);
        }
        return this;
    }
    public MatrixStacked3D hadamardMut(MatrixStacked3D mat) {
        int depth = depth();
        if(depth != mat.depth()){
            throw new IllegalArgumentException("Dotting 2 MatrixStacked3D with different depths");
        }

        for(int c = 0; c < depth; c++){
            this.matrices[c].hadamardMut(mat.matrices[c]);
        }
        return this;
    }

    public MatrixStacked3D dot(MatrixStacked3D mat) {
        int depth = depth();
        if(depth != mat.depth()){
            throw new IllegalArgumentException("Dotting 2 MatrixStacked3D with different depths");
        }

        Matrix2D firstResult = this.matrices[0].dot(mat.matrices[0]);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].dot(mat.matrices[c]);
        }
        return resultMatrices;
    }
    public MatrixStacked3D dotFlexible(MatrixStacked3D mat) {
        int depth = depth();
        if(depth != mat.depth()){
            throw new IllegalArgumentException("Dotting 2 MatrixStacked3D with different depths");
        }

        Matrix2D firstResult = this.matrices[0].dotFlexible(mat.matrices[0]);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].dotFlexible(mat.matrices[c]);
        }
        return resultMatrices;
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

        Matrix2D firstResult = this.matrices[0].convolve(kernel3D.matrices[0], options);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
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


        Matrix2D firstResult = this.convolve(kernel3D[0], options).collapseBySum();
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, kernel3D.length);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < resultMatrices.matrices.length; c++){
            resultMatrices.matrices[c] = this.convolve(kernel3D[c], options).collapseBySum();
        }
        return resultMatrices;
    }

    public MatrixStacked3D pooling(int filterSize, PoolingReducer reducer, ConvolutionOptions options){
        int depth = depth();
        if(options == null) options = new ConvolutionOptions();


        Matrix2D firstResult = this.matrices[0].pooling(filterSize, reducer, options);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].pooling(filterSize, reducer, options);
        }
        return resultMatrices;
    }

    public MatrixStacked3D dilate(int size){
        int depth = depth();

        Matrix2D firstResult = this.matrices[0].dilate(size);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].dilate(size);
        }
        return resultMatrices;
    }

    public MatrixStacked3D transpose() {
        int depth = depth();

        Matrix2D firstResult = this.matrices[0].transpose();
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].transpose();
        }
        return resultMatrices;
    }

    public MatrixStacked3D flip180(){
        int depth = depth();

        Matrix2D firstResult = this.matrices[0].flip180();
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].flip180();
        }
        return resultMatrices;
    }

    public MatrixStacked3D applyFunction(Function<Double, Double> func) {
        int depth = depth();

        Matrix2D firstResult = this.matrices[0].applyFunction(func);
        MatrixStacked3D resultMatrices = new MatrixStacked3D(firstResult.rows, firstResult.cols, depth);
        resultMatrices.matrices[0] = firstResult;

        for(int c = 1; c < depth; c++){
            resultMatrices.matrices[c] = this.matrices[c].applyFunction(func);
        }
        return resultMatrices;
    }
    public MatrixStacked3D applyFunctionMut(Function<Double, Double> func) {
        int depth = depth();
        for(int c = 0; c < depth; c++){
            this.matrices[c].applyFunctionMut(func);
        }
        return this;
    }

    // ------------- MatrixStacked3D specific stuff -------------
    public MatrixStacked3D flatten(){
        double[] res = new double[this.matrices.length * this.matrices[0].cols * this.matrices[0].rows];
        int outI = 0;
        for(int c = 0; c < matrices.length; c++){
            for(int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    res[outI++] = matrices[c].matrix[i][j];
                }
            }
        }
        return MatrixStacked3D.fromMatrix2D(Matrix2D.fromVect(res));
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
