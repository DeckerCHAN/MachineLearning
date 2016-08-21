package com.deckerchan.ml.classifier.k;

import com.deckerchan.ml.classifier.entities.RealItemHDPoint;

import java.util.List;

public final class KMeansClassifier extends KClassifierBase {

    public KMeansClassifier(int kValue, List<RealItemHDPoint> itemPoints) {
        super(kValue, itemPoints);
    }

    @Override
    protected void doBalance() {

    }
}
