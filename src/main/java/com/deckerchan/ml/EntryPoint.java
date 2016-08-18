package com.deckerchan.ml;

import com.deckerchan.ml.cluster.entities.Dimension;
import com.deckerchan.ml.cluster.entities.DimensionValueMap;
import com.deckerchan.ml.cluster.entities.HyperDimensionPoint;

public class EntryPoint {
    public static void main(String[] args) {
        Dimension x = new Dimension("x");
        Dimension y = new Dimension("y");

        DimensionValueMap m1p = new DimensionValueMap();
        m1p.put(x,2.0);
        m1p.put(y,4.9);
        HyperDimensionPoint p1 = new HyperDimensionPoint(m1p);

        DimensionValueMap m2p = new DimensionValueMap();
        m1p.put(x,3.0);
        m1p.put(y,7.9);
        HyperDimensionPoint p2 = new HyperDimensionPoint(m2p);
    }
}
