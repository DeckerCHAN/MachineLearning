package com.deckerchan.ml.classifier.entities;

import com.deckerchan.ml.classifier.utils.PointUtils;

import java.util.Collection;

public class RealItemHDPoint<E> extends HyperDimensionPoint {
    private E source;

    public RealItemHDPoint(E source,Coordinate coordinate) {
        super(coordinate);
        this.source = source;
    }

    public E getSource() {
        return source;
    }


    @Override
    public void setCoordinate(Coordinate coordinate) {
        throw new UnsupportedOperationException("You can not change dimension value map for real item point.");
    }

    public Cluster chooseNearestClusterPoint(Collection<Cluster> alternativePoints) {
        return alternativePoints.stream().min((p1, p2) -> Double.compare(PointUtils.cosineSimilarity(this, p1), PointUtils.cosineSimilarity(this, p2))).get();
    }

    @Override
    public String toString() {
        return String.format("The real item %d point for -> %s.", System.identityHashCode(this), this.source);
    }



}
