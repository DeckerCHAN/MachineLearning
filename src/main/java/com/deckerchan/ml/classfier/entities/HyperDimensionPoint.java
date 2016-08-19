package com.deckerchan.ml.classfier.entities;

import com.deckerchan.ml.classfier.utils.PointUtils;

import java.util.Map;

public final class HyperDimensionPoint {


    private Map<Dimension, Double> dimensionValueMap;

    public HyperDimensionPoint(Map<Dimension, Double> dimensionValueMap) {
        this.dimensionValueMap = dimensionValueMap;
    }

    public Map<Dimension, Double> getDimensionValueMap() {
        return dimensionValueMap;
    }

    public double getDistanceFrom(HyperDimensionPoint point) {
        return PointUtils.pointDistance(this, point);
    }

}
