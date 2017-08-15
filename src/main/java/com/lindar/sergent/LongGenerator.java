package com.lindar.sergent;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.rng.UniformRandomProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.LongSupplier;

public class LongGenerator {
    long randomProviderId;

    private long min;
    private long max;
    private List<Long> ignoreList;
    private long[] ignoreArray;

    LongGenerator(long min, long max, List<Long> ignoreList, long[] ignoreArray, long randomProviderId) {
        this(randomProviderId);
        this.min = min;
        this.max = max;
        this.ignoreList = ignoreList;
        this.ignoreArray = ignoreArray;
    }

    public LongGenerator(long randomProviderId) {
        this.randomProviderId = randomProviderId;
    }

    public LongGenerator withMinAndMax(long min, long max) {
        if (min <= 0) throw new IllegalArgumentException("Min has to be positive and greater than 0. Use the withMax method when you want min = 0");
        if (max <= 0) throw new IllegalArgumentException("Max has to be positive and greater than 0");
        return buildCopy().min(min).max(max).build();
    }

    public LongGenerator withMax(long max) {
        if (max <= 0) throw new IllegalArgumentException("Max has to be positive and greater than 0");
        return buildCopy().max(max).build();
    }

    public LongGenerator ignore(long... ignore) {
        return buildCopy().ignoreArray(ignore).build();
    }

    public LongGenerator ignore(List<Long> ignore) {
        return buildCopy().ignoreList(ignore).build();
    }

    public long randLong() {
        UniformRandomProvider randomProvider = RandomProviderFactory.getInstance(this.randomProviderId);

        LongSupplier randomValueSupplier;
        if (min > 0 && max > 0) {
            randomValueSupplier = () -> (randomProvider.nextLong((max - min) + 1) + min);
        } else if (max > 0) {
            randomValueSupplier = () -> randomProvider.nextLong(max);
        } else {
            randomValueSupplier = randomProvider::nextLong;
        }

        long randLong;
        do {
            randLong = randomValueSupplier.getAsLong();
        } while (shouldRejectValue(randLong));

        return randLong;
    }

    private boolean shouldRejectValue(long value) {
        boolean reject = false;
        if (ignoreList != null && !ignoreList.isEmpty() && ignoreList.contains(value)) {
            reject = true;
        }
        return reject || (ignoreArray != null && ArrayUtils.contains(ignoreArray, value));
    }

    private LongGeneratorBuilder buildCopy() {
        LongGeneratorBuilder generatorBuilder = new LongGeneratorBuilder(this.randomProviderId).min(this.min).max(this.max);
        if (this.ignoreList != null) {
            generatorBuilder.ignoreList(new ArrayList<>(this.ignoreList));
        }

        if (this.ignoreArray != null) {
            generatorBuilder.ignoreArray(this.ignoreArray.clone());
        }
        return generatorBuilder;
    }

    public long getMin() {
        return this.min;
    }

    public long getMax() {
        return this.max;
    }

    static class LongGeneratorBuilder {
        private long min;
        private long max;
        private List<Long> ignoreList;
        private long[] ignoreArray;

        private long randomProviderId;

        LongGeneratorBuilder(long randomProviderId) {
            this.randomProviderId = randomProviderId;
        }

        LongGenerator.LongGeneratorBuilder min(long min) {
            this.min = min;
            return this;
        }

        LongGenerator.LongGeneratorBuilder max(long max) {
            this.max = max;
            return this;
        }

        LongGenerator.LongGeneratorBuilder ignoreList(List<Long> ignoreList) {
            this.ignoreList = ignoreList;
            return this;
        }

        LongGenerator.LongGeneratorBuilder ignoreArray(long[] ignoreArray) {
            this.ignoreArray = ignoreArray;
            return this;
        }

        LongGenerator build() {
            return new LongGenerator(min, max, ignoreList, ignoreArray, randomProviderId);
        }
    }

    @Override
    public String toString() {
        return "LongGenerator{" +
                "min=" + min +
                ", max=" + max +
                ", ignoreList=" + ignoreList +
                ", ignoreArray=" + Arrays.toString(ignoreArray) +
                '}';
    }
}
