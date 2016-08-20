package com.deckerchan.ml.classifier.entities;

import java.util.Map;

public final class Cluster extends HyperDimensionPoint {

    private Map<HyperDimensionPoint, Double> relatedPointWithDistance;


    public Cluster(DimensionValueMap dimensionValueMap) {
        super(dimensionValueMap);

    }
}
