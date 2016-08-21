package com.deckerchan.ml.classifier.entities;

import com.deckerchan.ml.classifier.utils.PointUtils;

public class HyperDimensionPoint extends Object {


    private Coordinate coordinate;

    public HyperDimensionPoint(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public double getDistanceFrom(HyperDimensionPoint point) {
        return PointUtils.pointDistance(this, point);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
