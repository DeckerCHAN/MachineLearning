package com.deckerchan.ml.classifier.utils;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class RandomUtils {
    public static Set<Integer> generateRandomIntegers(int numbersNeeded, int bound) {
        if (numbersNeeded > bound) {
            throw new RuntimeException("Can not generate number more than bound");
        }
        Random rng = new Random();
        Set<Integer> generated = new LinkedHashSet<Integer>();
        while (generated.size() < numbersNeeded) {
            Integer next = rng.nextInt(bound);
            generated.add(next);
        }

        return generated;
    }
}
