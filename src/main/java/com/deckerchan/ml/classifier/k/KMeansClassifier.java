package com.deckerchan.ml.classifier.k;

import com.deckerchan.ml.classifier.entities.Cluster;
import com.deckerchan.ml.classifier.entities.RealItemHDPoint;
import com.deckerchan.ml.classifier.utils.PointUtils;

import java.util.List;

public final class KMeansClassifier extends KClassifierBase {

    public KMeansClassifier(int kValue, List<RealItemHDPoint> itemPoints) {
        super(kValue, itemPoints);
    }

    @Override
    protected void doBalance() {
        this.getClusters().parallelStream().forEach(
                cluster -> {
                        cluster.getDimensionValueMap()
                                .keySet().stream()
                                .forEach(dimension ->
                                {
                                    cluster.getDimensionValueMap()
                                            .set(dimension,
                                                    cluster.getRelatedPointWithDistance()
                                                            .keySet().stream()
                                                            .mapToDouble(
                                                                    hyperDimensionPoint ->
                                                                            hyperDimensionPoint.getDimensionValueMap()
                                                                                    .get(dimension))
                                                            .sum()/cluster.getRelatedPointWithDistance().size() );});

                });
    }
}
