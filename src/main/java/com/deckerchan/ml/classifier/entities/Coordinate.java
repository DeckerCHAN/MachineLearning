package com.deckerchan.ml.classifier.entities;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Coordinate extends HashMap<Dimension, Double> {

    public Coordinate(Collection<Dimension> dimensions) {
        dimensions.stream().forEach(dimension -> super.put(dimension, 0D));
    }

    @Override
    public Double put(Dimension key, Double value) {
        throw new UnsupportedOperationException("You can not set value!");
    }

    @Override
    public void putAll(Map<? extends Dimension, ? extends Double> m) {
        throw new UnsupportedOperationException("You can not set value!");
    }

    public void set(Dimension key, Double value) {
        if (this.containsKey(key)) {
            super.put(key, value);
        }
    }

    public void set(String dimensionName, Double value) {
        if (this.entrySet().stream().anyMatch(dimensionValueEntry -> dimensionValueEntry.getKey().getDimensionName().equals(dimensionName))) {
            this.entrySet().stream().filter(dimensionValueEntry -> dimensionValueEntry.getKey().getDimensionName().equals(dimensionName)).forEach(dimensionValueEntry -> dimensionValueEntry.setValue(value));
        }
    }

    public Set<Dimension> getDimensions(){
        return this.keySet();
    }

}
