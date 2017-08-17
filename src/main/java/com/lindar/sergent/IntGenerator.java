package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;

import java.util.*;
import java.util.stream.Collectors;

public class IntGenerator {

    long randomProviderSeed;

    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE - 1;
    private SortedSet<Integer> ignore = Collections.emptySortedSet();

    IntGenerator(int min, int max, List<Integer> ignoreList, long randomProviderSeed) {
        this(randomProviderSeed);
        this.min = min;
        this.max = max;

        if(ignoreList == null){
            this.ignore = Collections.emptySortedSet();
        } else {
            this.ignore = new TreeSet<>(ignoreList);
        }
    }

    public IntGenerator(long randomProviderSeed) {
        this.randomProviderSeed = randomProviderSeed;
    }

    public IntGenerator withMinAndMax(int min, int max) {
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
        UniformRandomProvider randomProvider = RandomProviderFactory.getInstance(this.randomProviderSeed);

        int origin = min;
        int bound = max + 1;

        int r = randomProvider.nextInt();
        int n = bound - origin - countIgnoreListInRange(), m = n - 1;
        if ((n & m) == 0) // power of two
            r = (r & m) + origin;
        else if (n > 0) { // reject over-represented candidates
            for (int u = r >>> 1; // ensure nonnegative
                u + m - (r = u % n) < 0; // rejection check
                u = randomProvider.nextInt() >>> 1); // retry
            r += origin;
        }
        else { // range not representable
            while (r < origin || r >= bound || ignore.contains(r))
                r = randomProvider.nextInt();
            return r;
        }

        for (Integer exclude : ignore) {
            if(exclude < min || exclude > max){
                continue;
            }
            if (exclude > r) {
                return r;
            }

            r++;
        }

        return r;
    }

    private int countIgnoreListInRange(){
        if(ignore.isEmpty()) return 0;

        return (int) ignore.stream().filter(i -> i >= min && i <= max).count();
    }

    private IntGeneratorBuilder buildCopy() {
        IntGeneratorBuilder generatorBuilder = new IntGeneratorBuilder(this.randomProviderSeed).min(this.min).max(this.max);
        if (this.ignore != null) {
            generatorBuilder.ignore(new ArrayList<>(this.ignore));
        }
        return generatorBuilder;
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

        private long randomProviderSeed;

        IntGeneratorBuilder(long randomProviderSeed) {
            this.randomProviderSeed = randomProviderSeed;
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
