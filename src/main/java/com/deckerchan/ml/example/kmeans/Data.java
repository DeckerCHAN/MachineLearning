package com.deckerchan.ml.example.kmeans;

class Data {
    private double mX = 0;
    private double mY = 0;
    private int mCluster = 0;

    public Data() {
    }

    public Data(double x, double y) {
        this.X(x);
        this.Y(y);
    }

    public void X(double x) {
        this.mX = x;
    }

    public double X() {
        return this.mX;
    }

    public void Y(double y) {
        this.mY = y;
    }

    public double Y() {
        return this.mY;
    }

    public void cluster(int clusterNumber) {
        this.mCluster = clusterNumber;
    }

    public int cluster() {
        return this.mCluster;
    }
}