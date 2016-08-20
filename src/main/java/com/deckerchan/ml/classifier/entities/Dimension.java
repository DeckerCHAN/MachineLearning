package com.deckerchan.ml.classifier.entities;

public final class Dimension {

    private String dimensionName;
    private double weight;

    public Dimension(String dimensionName) {
        this.dimensionName = dimensionName;
        this.weight = 1;
    }

    public Dimension(String dimensionName, double weight) {
        this(dimensionName);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public String getDimensionName() {
        return dimensionName;
    }


}
