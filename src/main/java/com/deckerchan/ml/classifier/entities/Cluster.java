package com.deckerchan.ml.classifier.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class Cluster extends HyperDimensionPoint {

    public Map<HyperDimensionPoint, Double> getRelatedPointWithDistance() {
        return relatedPointWithDistance;
    }

    private void setRelatedPointWithDistance(Map<HyperDimensionPoint, Double> relatedPointWithDistance) {
        this.relatedPointWithDistance = relatedPointWithDistance;
    }

    private Map<HyperDimensionPoint, Double> relatedPointWithDistance;


    public Cluster(DimensionValueMap dimensionValueMap) {
        super(dimensionValueMap);
        this.relatedPointWithDistance = new HashMap<>();
    }

    @Override
    public String toString() {
        return String.format("The cluster %d contains related points %d.%n", System.identityHashCode(this), this.relatedPointWithDistance.size());
    }


}
