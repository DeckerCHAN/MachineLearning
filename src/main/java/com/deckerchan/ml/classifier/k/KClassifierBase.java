package com.deckerchan.ml.classifier.k;

import com.deckerchan.ml.classifier.entities.RealItemHDPoint;

import java.util.List;

public abstract class KClassifierBase {
    private int kValue;
    private List<RealItemHDPoint> itemPoints;

    public KClassifierBase(int kValue, List<RealItemHDPoint> itemPoints) {
        this.kValue = kValue;
        this.itemPoints = itemPoints;
    }

    public int getkValue() {
        return kValue;
    }

    protected void setkValue(int kValue) {
        this.kValue = kValue;
    }

    public List<RealItemHDPoint> getItemPoints() {
        return itemPoints;
    }

    protected void setItemPoints(List<RealItemHDPoint> itemPoints) {
        this.itemPoints = itemPoints;
    }
}
