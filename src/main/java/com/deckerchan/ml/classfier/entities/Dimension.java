package com.deckerchan.ml.classfier.entities;

public final class Dimension {

    private String dimensionName;
    private int weight;

    public Dimension(String dimensionName) {
        this.dimensionName = dimensionName;
        this.weight = 1;
    }

    public Dimension(String dimensionName, int weight) {
        this(dimensionName);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public String getDimensionName() {
        return dimensionName;
    }


}
