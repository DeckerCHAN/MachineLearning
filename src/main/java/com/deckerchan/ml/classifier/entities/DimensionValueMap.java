package com.deckerchan.ml.classifier.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class DimensionValueMap extends HashMap<Dimension, Long> {

    public DimensionValueMap(Collection<Dimension> dimensions) {
        dimensions.stream().forEach(dimension -> super.put(dimension, 0L));
    }

    @Override
    public Long put(Dimension key, Long value) {
        throw new UnsupportedOperationException("You can not set value!");
    }

    @Override
    public void putAll(Map<? extends Dimension, ? extends Long> m) {
        throw new UnsupportedOperationException("You can not set value!");
    }



}
