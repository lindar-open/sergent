package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListGenerator {
    private Long randomProviderSeed;

    private int min;
    private int max;
    private int listSize;
    private boolean unique;

    ListGenerator(int min, int max, int listSize, boolean unique, Long randomProviderSeed) {
        this.randomProviderSeed = randomProviderSeed;
        this.min = min;
        this.max = max;
        this.listSize = listSize;
        this.unique = unique;
    }

    ListGenerator() {
    }

    public ListGenerator withSeed(Long seed) {
        return buildCopy().seed(seed).build();
    }

    public ListGenerator withMinAndMax(int min, int max) {
        if (min <= 0) throw new IllegalArgumentException("Min has to be positive and greater than 0. Use the withMax method when you want min = 0");
        if (max <= 0) throw new IllegalArgumentException("Max has to be positive and greater than 0");
        return buildCopy().min(min).max(max).build();
    }

    public ListGenerator withMax(int max) {
        if (max <= 0) throw new IllegalArgumentException("Max has to be positive and greater than 0");
        return buildCopy().max(max).build();
    }

    public ListGenerator ofSize(int listSize) {
        if (listSize <= 0) throw new IllegalArgumentException("List size has to be positive and greater than 0");
        return buildCopy().listSize(listSize).build();
    }

    public ListGenerator unique() {
        return buildCopy().unique(true).build();
    }

    /**
     * This method generates a random list of integers between min and max. <br/>
     * NOTE: If not unique and list size != max-min there is no guarantee the returned list is uniform (every integer number between min and max)
     */
    public List<Integer> randIntegers() {

        if (unique && (listSize == 0 || listSize == max - min)) {
            List<Integer> numbers = IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());
            new Shuffler().withSeed(randomProviderSeed).list(numbers);
            return numbers;
        }

        UniformRandomProvider randomProvider = RandomProviderFactory.getInstance(this.randomProviderSeed);

        int diff = max - min + 1;
        List<Integer> randomList = new ArrayList<>(listSize);
        IntStream.range(0, listSize).forEach(index -> {
            IntGenerator intGen = new IntGenerator().withSeed(this.randomProviderSeed);
            if (max > 0 && min > 0) {
                intGen = intGen.withMinAndMax(min, max);
            } else if (max > 0) {
                intGen = intGen.withMax(max);
            }
            if (unique && diff >= listSize) {
                intGen = intGen.ignore(randomList);
            }
            randomList.add(intGen.randInt(randomProvider));
        });

        return randomList;
    }

    private ListGeneratorBuilder buildCopy() {
        return new ListGeneratorBuilder().seed(this.randomProviderSeed)
                .min(this.min).max(this.max).listSize(this.listSize).unique(this.unique);
    }

    static class ListGeneratorBuilder {
        private Long randomProviderSeed;

        private int min;
        private int max;
        private int listSize;
        private boolean unique;

        ListGenerator.ListGeneratorBuilder seed(Long randomProviderSeed) {
            this.randomProviderSeed = randomProviderSeed;
            return this;
        }

        ListGenerator.ListGeneratorBuilder min(int min) {
            this.min = min;
            return this;
        }

        ListGenerator.ListGeneratorBuilder max(int max) {
            this.max = max;
            return this;
        }

        ListGenerator.ListGeneratorBuilder listSize(int listSize) {
            this.listSize = listSize;
            return this;
        }

        ListGenerator.ListGeneratorBuilder unique(boolean unique) {
            this.unique = unique;
            return this;
        }

        ListGenerator build() {
            return new ListGenerator(min, max, listSize, unique, randomProviderSeed);
        }
    }
}
