package com.lindar.sergent;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.rng.UniformRandomProvider;

import java.util.List;
import java.util.function.IntSupplier;

class IntGenerator {

    private final UniformRandomProvider randomProvider;

    IntGenerator(UniformRandomProvider randomProvider) {
        this.randomProvider = randomProvider;
    }

    private int min;
    private int max;
    private List<Integer> ignoreList;
    private int[] ignoreArray;

    public IntGenerator withMinAndMax(int min, int max) {
        if (min <= 0) throw new IllegalArgumentException("Min has to be positive and greater than 0. Use the withMax method when you want min = 0");
        this.min = min;
        return withMax(max);
    }

    public IntGenerator withMax(int max) {
        if (max <= 0) throw new IllegalArgumentException("Max has to be positive and greater than 0");
        this.max = max;
        return this;
    }

    public IntGenerator ignore(int... ignore) {
        this.ignoreArray = ignore;
        return this;
    }

    public IntGenerator ignore(List<Integer> ignore) {
        this.ignoreList = ignore;
        return this;
    }

    public int randInt() {
        int randInt = 0;

        IntSupplier randomValueSupplier;
        if (min > 0 && max > 0) {
            randomValueSupplier = () -> (randomProvider.nextInt((max - min) + 1) + min);
        } else if (max > 0) {
            randomValueSupplier = () -> randomProvider.nextInt(max);
        } else {
            randomValueSupplier = randomProvider::nextInt;
        }

        while (shouldRejectValue(randInt)) {
            randInt = randomValueSupplier.getAsInt();
        }

        return randInt;
    }

    private boolean shouldRejectValue(int value) {
        boolean reject = false;
        if (ignoreList != null && !ignoreList.isEmpty() && ignoreList.contains(value)) {
            reject = true;
        }
        return reject || (ignoreArray != null && ArrayUtils.contains(ignoreArray, value));
    }
}
