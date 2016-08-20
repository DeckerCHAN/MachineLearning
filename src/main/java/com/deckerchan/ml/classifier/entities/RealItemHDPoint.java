package com.deckerchan.ml.classifier.entities;

import com.deckerchan.ml.classifier.utils.PointUtils;

import java.util.Collection;

public class RealItemHDPoint<E> extends HyperDimensionPoint {
    private E source;

    public RealItemHDPoint(DimensionValueMap dimensionValueMap, E source) {
        super(dimensionValueMap);
        this.source = source;
    }

    public E getSource() {
        return source;
    }


    public <T extends HyperDimensionPoint> T chooseNearestPoint(Collection<T> alternativePoints) {
        return alternativePoints.stream().min((p1, p2) -> Double.compare(PointUtils.pointDistance(this, p1), PointUtils.pointDistance(this, p2))).get();
    }
}
