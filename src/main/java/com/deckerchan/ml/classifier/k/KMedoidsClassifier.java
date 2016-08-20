package com.deckerchan.ml.classifier.k;

import com.deckerchan.ml.classifier.entities.RealItemHDPoint;

import java.util.List;

public final class KMedoidsClassifier extends KClassifierBase {


    public KMedoidsClassifier(int kValue, List<RealItemHDPoint> itemPoints) {
        super(kValue, itemPoints);
    }
}
