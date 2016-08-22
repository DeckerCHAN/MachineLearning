package com.deckerchan.ml.classifier.entities;

import java.util.*;

public class WordFrequencyBasedValueTable extends Hashtable<String, Double> {

    public WordFrequencyBasedValueTable() {
        super();
    }

    private static Comparator<Map.Entry<String, Double>> getComparatpr() {
        return (o1, o2) -> {
            if (o1.getValue() < o2.getValue()) {
                return 1;
            } else if (o1.getValue() > o2.getValue()) {
                return -1;
            } else {
                return 0;
            }
        };


    }

    public synchronized int getTotalWords() {
        return this.size();
    }

    public synchronized double getTotalOccurance() {
        return this.values().stream().reduce(0D, (long1, long2) -> long1 + long2);
    }

    public synchronized void occure(String word, Double value) {

        if (this.containsKey(word)) {
            this.put(word, this.get(word) + value);
        } else {
            this.put(word, value);
        }


    }


    public synchronized void accumulate(String word, Double value) {
        if (this.containsKey(word)) {
            this.put(word, this.get(word) + value);
        } else {
            this.put(word, value);
        }
    }

    public synchronized WordFrequencyBasedValueTable mergeTable(WordFrequencyBasedValueTable table) {
        for (Map.Entry<String, Double> entry : table.entrySet()) {
            this.accumulate(entry.getKey(), entry.getValue());
        }

        return this;

    }

    public synchronized WordFrequencyBasedValueTable mergeTable(Map<String, Double> table) {
        for (Map.Entry<String, Double> entry : table.entrySet()) {
            this.accumulate(entry.getKey(), entry.getValue());
        }

        return this;

    }

    public synchronized LinkedHashMap<String, Double> getSortedTableOrderByValue() {


        return this.entrySet().stream()
                .sorted(getComparatpr())
                .collect(LinkedHashMap<String, Double>::new,
                        (stringLongLinkedHashMap, stringLongEntry) -> stringLongLinkedHashMap.put(stringLongEntry.getKey(),
                                stringLongEntry.getValue()),
                        HashMap::putAll);
    }

    public synchronized LinkedHashMap<String, Double> getSortedTableOrderByValue(int top) {


        return this.entrySet().stream()
                .sorted(getComparatpr()).limit(top)
                .collect(LinkedHashMap<String, Double>::new,
                        (stringLongLinkedHashMap, stringLongEntry) -> stringLongLinkedHashMap.put(stringLongEntry.getKey(),
                                stringLongEntry.getValue()),
                        HashMap::putAll);
    }

    public synchronized Set<String> getWords() {
        return this.keySet();
    }


}
