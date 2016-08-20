package com.deckerchan.ml.classifier.entities;

import com.deckerchan.ml.classifier.utils.PointUtils;

public class HyperDimensionPoint {


    private DimensionValueMap dimensionValueMap;

    public HyperDimensionPoint(DimensionValueMap dimensionValueMap) {
        this.dimensionValueMap = dimensionValueMap;
    }

    public DimensionValueMap getDimensionValueMap() {
        return dimensionValueMap;
    }

    protected void setDimensionValueMap(DimensionValueMap dimensionValueMap) {
        this.dimensionValueMap = dimensionValueMap;
    }

    public double getDistanceFrom(HyperDimensionPoint point) {
        return PointUtils.pointDistance(this, point);
    }

}
