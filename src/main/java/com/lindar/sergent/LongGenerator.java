package com.lindar.sergent;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.rng.UniformRandomProvider;

import java.util.List;
import java.util.function.LongSupplier;

class LongGenerator {

    private final UniformRandomProvider randomProvider;

    LongGenerator(UniformRandomProvider randomProvider) {
        this.randomProvider = randomProvider;
    }

    private long min;
    private long max;
    private List<Long> ignoreList;
    private long[] ignoreArray;

    public LongGenerator withMinAndMax(long min, long max) {
        if (min <= 0) throw new IllegalArgumentException("Min has to be positive and greater than 0. Use the withMax method when you want min = 0");
        this.min = min;
        return withMax(max);
    }

    public LongGenerator withMax(long max) {
        if (max <= 0) throw new IllegalArgumentException("Max has to be positive and greater than 0");
        this.max = max;
        return this;
    }

    public LongGenerator ignore(long... ignore) {
        this.ignoreArray = ignore;
        return this;
    }

    public LongGenerator ignore(List<Long> ignore) {
        this.ignoreList = ignore;
        return this;
    }

    public long randLong() {
        long randLong = 0;

        LongSupplier randomValueSupplier;
        if (min > 0 && max > 0) {
            randomValueSupplier = () -> (randomProvider.nextLong((max - min) + 1) + min);
        } else if (max > 0) {
            randomValueSupplier = () -> randomProvider.nextLong(max);
        } else {
            randomValueSupplier = randomProvider::nextLong;
        }

        while (shouldRejectValue(randLong)) {
            randLong = randomValueSupplier.getAsLong();
        }

        return randLong;
    }

    private boolean shouldRejectValue(long value) {
        boolean reject = false;
        if (ignoreList != null && !ignoreList.isEmpty() && ignoreList.contains(value)) {
            reject = true;
        }
        return reject || (ignoreArray != null && ArrayUtils.contains(ignoreArray, value));
    }
}
