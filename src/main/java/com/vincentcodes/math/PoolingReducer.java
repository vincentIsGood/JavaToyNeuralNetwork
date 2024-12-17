package com.vincentcodes.math;

public interface PoolingReducer {
    void init(int len);
    void compute(double n);
    double result();

    class MaxPooling implements PoolingReducer{
        private double max;

        public void init(int len){
            this.max = -Double.MAX_VALUE;
        }

        public void compute(double n){
            if(n > this.max) this.max = n;
        }

        public double result(){
            return this.max;
        }
    }

    class AvgPooling implements PoolingReducer{
        private double avg;
        private int len;

        public void init(int len){
            this.avg = 0;
            this.len = len;
        }

        public void compute(double n){
            this.avg += n;
        }

        public double result(){
            return this.avg / this.len;
        }
    }
}
