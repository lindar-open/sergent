package com.lindar.sergent;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.rng.UniformRandomProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntSupplier;

public class IntGenerator {

    long randomProviderId;

    private int min;
    private int max;
    private List<Integer> ignoreList;
    private int[] ignoreArray;

    IntGenerator(int min, int max, List<Integer> ignoreList, int[] ignoreArray, long randomProviderId) {
        this(randomProviderId);
        this.min = min;
        this.max = max;
        this.ignoreList = ignoreList;
        this.ignoreArray = ignoreArray;
    }

    public IntGenerator(long randomProviderId) {
        this.randomProviderId = randomProviderId;
    }

    public IntGenerator withMinAndMax(int min, int max) {
        if (min <= 0) throw new IllegalArgumentException("Min has to be positive and greater than 0. Use the withMax method when you want min = 0");
        if (max <= 0) throw new IllegalArgumentException("Max has to be positive and greater than 0");
        return buildCopy().min(min).max(max).build();
    }

    public IntGenerator withMax(int max) {
        if (max <= 0) throw new IllegalArgumentException("Max has to be positive and greater than 0");
        return buildCopy().max(max).build();
    }

    public IntGenerator ignore(int... ignore) {
        return buildCopy().ignoreArray(ignore).build();
    }

    public IntGenerator ignore(List<Integer> ignore) {
        return buildCopy().ignoreList(ignore).build();
    }

    public int randInt() {
        UniformRandomProvider randomProvider = RandomProviderFactory.getInstance(this.randomProviderId);

        IntSupplier randomValueSupplier;
        if (min > 0 && max > 0) {
            randomValueSupplier = () -> (randomProvider.nextInt((max - min) + 1) + min);
        } else if (max > 0) {
            randomValueSupplier = () -> randomProvider.nextInt(max);
        } else {
            randomValueSupplier = randomProvider::nextInt;
        }

        int randInt;
        do {
            randInt = randomValueSupplier.getAsInt();
        } while (shouldRejectValue(randInt));

        return randInt;
    }

    private boolean shouldRejectValue(int value) {
        boolean reject = false;
        if (ignoreList != null && !ignoreList.isEmpty() && ignoreList.contains(value)) {
            reject = true;
        }
        return reject || (ignoreArray != null && ArrayUtils.contains(ignoreArray, value));
    }

    private IntGeneratorBuilder buildCopy() {
        IntGeneratorBuilder generatorBuilder = new IntGeneratorBuilder(this.randomProviderId).min(this.min).max(this.max);
        if (this.ignoreList != null) {
            generatorBuilder.ignoreList(new ArrayList<>(this.ignoreList));
        }

        if (this.ignoreArray != null) {
            generatorBuilder.ignoreArray(this.ignoreArray.clone());
        }
        return generatorBuilder;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    private static class IntGeneratorBuilder {
        private int min;
        private int max;
        private List<Integer> ignoreList;
        private int[] ignoreArray;

        private long randomProviderId;

        IntGeneratorBuilder(long randomProviderId) {
            this.randomProviderId = randomProviderId;
        }

        IntGenerator.IntGeneratorBuilder min(int min) {
            this.min = min;
            return this;
        }

        IntGenerator.IntGeneratorBuilder max(int max) {
            this.max = max;
            return this;
        }

        IntGenerator.IntGeneratorBuilder ignoreList(List<Integer> ignoreList) {
            this.ignoreList = ignoreList;
            return this;
        }

        IntGenerator.IntGeneratorBuilder ignoreArray(int[] ignoreArray) {
            this.ignoreArray = ignoreArray;
            return this;
        }

        IntGenerator build() {
            return new IntGenerator(min, max, ignoreList, ignoreArray, randomProviderId);
        }
    }

    @Override
    public String toString() {
        return "IntGenerator{" +
                "min=" + min +
                ", max=" + max +
                ", ignoreList=" + ignoreList +
                ", ignoreArray=" + Arrays.toString(ignoreArray) +
                '}';
    }
}
