package com.vincentcodes.math;

import java.util.Arrays;

public class Vector {
    public double[] vect;
    public int rows;

    public Vector(int rows) {
        this.rows = rows;
        vect = new double[rows];

        for(int row = 0; row < this.rows; ++row) {
            this.vect[row] = 0;
        }
    }

    /**
     * @param vect will mutate vect
     */
    public Vector(double[] vect) {
        this.rows = vect.length;
        this.vect = vect;
    }

    public static Vector fromArray(double[] vect){
        return new Vector(vect);
    }

    public Vector add(Vector b){
        Vector vector = new Vector(rows);
        for(int i = 0; i < rows; i++){
            vector.vect[i] = this.vect[i] + b.vect[i];
        }
        return vector;
    }
    public Vector addMut(Vector b){
        Vector vector = this;
        for(int i = 0; i < rows; i++){
            vector.vect[i] = this.vect[i] + b.vect[i];
        }
        return vector;
    }

    public Vector sub(Vector b){
        Vector vector = new Vector(rows);
        for(int i = 0; i < rows; i++){
            vector.vect[i] = this.vect[i] - b.vect[i];
        }
        return vector;
    }
    public Vector subMut(Vector b){
        Vector vector = this;
        for(int i = 0; i < rows; i++){
            vector.vect[i] = this.vect[i] - b.vect[i];
        }
        return vector;
    }

    public Vector multiply(double n){
        Vector vector = new Vector(rows);
        for(int i = 0; i < rows; i++){
            vector.vect[i] = this.vect[i] * n;
        }
        return vector;
    }
    public Vector multiplyMut(double n){
        Vector vector = this;
        for(int i = 0; i < rows; i++){
            vector.vect[i] = this.vect[i] * n;
        }
        return vector;
    }

    public Vector divide(double n){
        Vector vector = new Vector(rows);
        for(int i = 0; i < rows; i++){
            vector.vect[i] = this.vect[i] / n;
        }
        return vector;
    }
    public Vector divideMut(double n){
        Vector vector = this;
        for(int i = 0; i < rows; i++){
            vector.vect[i] = this.vect[i] / n;
        }
        return vector;
    }

    public double dot(Vector b){
        double res = 0;
        for(int i = 0; i < rows; i++){
            res += this.vect[i] * b.vect[i];
        }
        return res;
    }

    public Vector normalized(){
        return this.divide(selfDistance());
    }

    public double sum(){
        double sum = 0;
        for(int i = 0; i < rows; i++){
            sum += this.vect[i];
        }
        return sum;
    }

    public double selfDistance(){
        double sum = 0;
        for(int i = 0; i < rows; i++){
            sum += this.vect[i]*this.vect[i];
        }
        return Math.sqrt(sum);
    }

    public Matrix toMatrix(){
        Matrix res = new Matrix(rows, 1);
        for(int i = 0; i < rows; i++){
            res.matrix[i][0] = vect[i];
        }
        return res;
    }

    public String toString(){
        return Arrays.toString(vect);
    }
}
