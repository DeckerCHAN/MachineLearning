package com.deckerchan.ml.classifier.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Cluster extends HyperDimensionPoint {

    private List<HyperDimensionPoint> relatedPointList;


    public Cluster(Coordinate coordinate) {
        super(coordinate);
        this.relatedPointList = new ArrayList<>();
    }

    public Map<HyperDimensionPoint, Double> getRelatedPointWithDistance() {
        Map<HyperDimensionPoint, Double> map = new HashMap<>();
        this.relatedPointList.stream().forEach(hyperDimensionPoint -> map.put(hyperDimensionPoint, hyperDimensionPoint.getDistanceFrom(this)));
        return map;
    }

    public List<HyperDimensionPoint> getRelatedPointList() {
        return relatedPointList;
    }

    private void setRelatedPointList(List<HyperDimensionPoint> relatedPointList) {
        this.relatedPointList = relatedPointList;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("The cluster %d contains related points %d.%n", System.identityHashCode(this), this.relatedPointList.size()));
        Map<HyperDimensionPoint, Double> pd = this.getRelatedPointWithDistance();

        for (Map.Entry<HyperDimensionPoint, Double> entry : pd.entrySet()) {
            res.append(String.format("[Dis:%f]%s%n", entry.getValue(), entry.getKey().toString()));
        }

        return res.toString();
    }


}
