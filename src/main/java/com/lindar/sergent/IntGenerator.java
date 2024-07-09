package com.lindar.sergent;

import java.util.*;
import java.util.stream.Collectors;

public class IntGenerator {

    private SergentRNG sergentRNG = new SergentRNG();

    private Long randomProviderSeed;

    private int min = 0;
    private int max = Integer.MAX_VALUE - 1;
    private SortedSet<Integer> ignore = Collections.emptySortedSet();

    IntGenerator(int min, int max, List<Integer> ignoreList, Long randomProviderSeed) {
        this.randomProviderSeed = randomProviderSeed;
        this.min = min;
        this.max = max;

        if(ignoreList == null){
            this.ignore = Collections.emptySortedSet();
        } else {
            this.ignore = new TreeSet<>(ignoreList);
        }
    }

    IntGenerator() {
    }

    public IntGenerator withSeed(Long seed) {
        return buildCopy().seed(seed).build();
    }

    public IntGenerator withMinAndMax(int min, int max) {
        if (min<0) throw new IllegalArgumentException("Min has to be positive or 0");
        if (max <= min || max == Integer.MAX_VALUE) throw new IllegalArgumentException("Max has to be greater then Min and less then Integer.MAX_VALUE");
        return buildCopy().min(min).max(max).build();
    }

    public IntGenerator withMax(int max) {
        if (max <= 0 || max == Integer.MAX_VALUE) throw new IllegalArgumentException("Max has to be positive and greater than 0 and less then Integer.MAX_VALUE");
        return buildCopy().min(0).max(max).build();
    }

    public IntGenerator ignore(int... ignore) {
        return buildCopy().ignore(ignore).build();
    }

    public IntGenerator ignore(List<Integer> ignore) {
        return buildCopy().ignore(ignore).build();
    }

    public int randInt() {
        return randInt(sergentRNG);
    }

    int randInt(SergentRNG randomProvider) {
        int number = randomProvider.nextInt(min, max);
        while (ignore.contains(number)) {
            number = randomProvider.nextInt(min, max);
        }
        return number;
    }

    private IntGeneratorBuilder buildCopy() {
        IntGeneratorBuilder generatorBuilder = new IntGeneratorBuilder().seed(randomProviderSeed).min(this.min).max(this.max);
        if (this.ignore != null) {
            generatorBuilder.ignore(new ArrayList<>(this.ignore));
        }
        return generatorBuilder;
    }

    Long getRandomProviderSeed() {
        return this.randomProviderSeed;
    }

    public int getMin() {
        return this.min;
    }

    public int getMax() {
        return this.max;
    }

    static class IntGeneratorBuilder {
        private int min = Integer.MIN_VALUE;
        private int max = Integer.MAX_VALUE - 1;
        private List<Integer> ignore;

        private Long randomProviderSeed;

        IntGenerator.IntGeneratorBuilder seed(Long randomProviderSeed) {
            this.randomProviderSeed = randomProviderSeed;
            return this;
        }

        IntGenerator.IntGeneratorBuilder min(int min) {
            this.min = min;
            return this;
        }

        IntGenerator.IntGeneratorBuilder max(int max) {
            this.max = max;
            return this;
        }

        IntGenerator.IntGeneratorBuilder ignore(List<Integer> ignore) {
            this.ignore = ignore;
            return this;
        }

        IntGenerator.IntGeneratorBuilder ignore(int[] ignoreArray) {
            this.ignore = Arrays.stream(ignoreArray).boxed().collect(Collectors.toList());
            return this;
        }

        IntGenerator build() {
            return new IntGenerator(min, max, ignore, randomProviderSeed);
        }
    }

    @Override
    public String toString() {
        return "IntGenerator{" +
                "min=" + min +
                ", max=" + max +
                ", ignore=" + ignore +
                '}';
    }
}
