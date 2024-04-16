package com.vincentcodes.math;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.function.Function;

public class Matrix {
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

    public Matrix transpose() {
        Matrix res = new Matrix(this.cols, this.rows);

        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                res.matrix[col][row] = this.matrix[row][col];
            }
        }

        return res;
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

    public void copy(Matrix mat) {
        for(int row = 0; row < this.rows; ++row) {
            for(int col = 0; col < this.cols; ++col) {
                this.matrix[row][col] = mat.matrix[row][col];
            }
        }

    }

    public static double[][] getArrayFromMatrix(Matrix mat) {
        double[][] var1 = new double[mat.rows][mat.cols];

        for(int row = 0; row < mat.rows; ++row) {
            for(int col = 0; col < mat.cols; ++col) {
                var1[row][col] = mat.matrix[row][col];
            }
        }

        return var1;
    }

    public static Matrix getMatrixFromArray(double[][] mat) {
        Matrix res = new Matrix(mat.length, mat[0].length);

        for(int row = 0; row < res.rows; ++row) {
            for(int col = 0; col < res.cols; ++col) {
                res.matrix[row][col] = mat[row][col];
            }
        }

        return res;
    }

    public static Matrix getVectMatrixFromArray(double[] vect) {
        Matrix res = new Matrix(vect.length, 1);

        for(int row = 0; row < res.rows; ++row) {
            res.matrix[row][0] = vect[row];
        }

        return res;
    }

    public String dimString(){
        return "(" + this.rows + ", " + this.cols + ")";
    }

    public String toString() {
        StringBuilder res = new StringBuilder();

        for(int row = 0; row < this.matrix.length; ++row) {
            for(int col = 0; col < this.matrix[0].length; ++col) {
                res.append(this.matrix[row][col]).append(" ");
            }

            res.append("\n");
        }

        return res.toString();
    }
}
