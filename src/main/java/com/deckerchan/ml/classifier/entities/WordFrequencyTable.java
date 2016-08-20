package com.deckerchan.ml.classifier.entities;

import java.util.*;

public class WordFrequencyTable extends Hashtable<String, Long> {

    public WordFrequencyTable() {
        super();
    }

    public synchronized int getTotalWords() {
        return this.size();
    }

    public synchronized long getTotalOccurance() {
        return this.values().stream().reduce(0L, (long1, long2) -> long1 + long2);
    }


    public synchronized void occure(String word) {
        if (this.containsKey(word)) {
            this.put(word, this.get(word) + 1L);
        } else {
            this.put(word, 1L);
        }


    }


    public synchronized void occure(String word, Long times) {
        if (this.containsKey(word)) {
            this.put(word, this.get(word) + times);
        } else {
            this.put(word, times);
        }


    }

    public synchronized void occureAll(Collection<String> words) {
        for (String word : words) {
            if (this.containsKey(word)) {
                this.put(word, this.get(word) + 1L);
            } else {
                this.put(word, 1L);
            }
        }
    }

    public synchronized WordFrequencyTable mergeTable(WordFrequencyTable table) {
        for (Map.Entry<String, Long> entry : table.entrySet()) {
            this.occure(entry.getKey(), entry.getValue());
        }

        return this;

    }

    public synchronized LinkedHashMap<String, Long> getSortedTableOrderByValue() {


        return this.entrySet().stream()
                .sorted(Map.Entry.comparingByValue((o1, o2) -> {
                    if (o1 < o2) {
                        return 1;
                    } else if (o1 > o2) {
                        return -1;
                    } else {
                        return 0;
                    }
                }))
                .collect(LinkedHashMap<String, Long>::new,
                        (stringLongLinkedHashMap, stringLongEntry) -> stringLongLinkedHashMap.put(stringLongEntry.getKey(),
                                stringLongEntry.getValue()),
                        HashMap::putAll);
    }


}
