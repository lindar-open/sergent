package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;

import java.util.*;
import java.util.stream.Collectors;

public class LongGenerator {

    long randomProviderSeed;
    private long min = Long.MIN_VALUE;
    private long max = Long.MAX_VALUE - 1;
    private SortedSet<Long> ignore = Collections.emptySortedSet();

    LongGenerator(long min, long max, List<Long> ignoreList, long randomProviderSeed) {
        this(randomProviderSeed);
        this.min = min;
        this.max = max;

        if(ignoreList == null){
            this.ignore = Collections.emptySortedSet();
        } else {
            this.ignore = new TreeSet<>(ignoreList);
        }
    }

    public LongGenerator(long randomProviderSeed) {
        this.randomProviderSeed = randomProviderSeed;
    }

    public LongGenerator withMinAndMax(long min, long max) {
        if (max <= min || max == Long.MAX_VALUE) throw new IllegalArgumentException("Max has to be greater then Min and less then Long.MAX_VALUE");
        return buildCopy().min(min).max(max).build();
    }

    public LongGenerator withMax(long max) {
        if (max <= 0 || max == Long.MAX_VALUE) throw new IllegalArgumentException("Max has to be positive and greater than 0 and less then Long.MAX_VALUE");
        return buildCopy().min(0).max(max).build();
    }

    public LongGenerator ignore(long... ignore) {
        return buildCopy().ignore(ignore).build();
    }

    public LongGenerator ignore(List<Long> ignore) {
        return buildCopy().ignore(ignore).build();
    }

    public long randLong(){
        UniformRandomProvider randomProvider = RandomProviderFactory.getInstance(randomProviderSeed);

        long origin = min;
        long bound = max + 1;

        long r = randomProvider.nextLong();
        long n = bound - origin - countIgnoreListInRange(), m = n - 1;
        if ((n & m) == 0) // power of two
            r = (r & m) + origin;
        else if (n > 0) { // reject over-represented candidates
            for (long u = r >>> 1; // ensure nonnegative
                 u + m - (r = u % n) < 0; // rejection check
                 u = randomProvider.nextLong() >>> 1); // retry
            r += origin;
        }
        else { // range not representable
            while (r < origin || r >= bound || ignore.contains(r))
                r = randomProvider.nextLong();
            return r;
        }

        for (Long exclude : ignore) {
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

    private long countIgnoreListInRange(){
        if(ignore.isEmpty()) return 0;

        return ignore.stream().filter(i -> i >= min && i <= max).count();
    }

    private LongGeneratorBuilder buildCopy() {
        LongGeneratorBuilder generatorBuilder = new LongGeneratorBuilder(randomProviderSeed).min(this.min).max(this.max);
        if (this.ignore != null) {
            generatorBuilder.ignore(new ArrayList<>(this.ignore));
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
        private long min = Long.MIN_VALUE;
        private long max = Long.MAX_VALUE - 1;
        private List<Long> ignore;

        private long randomProviderSeed;

        LongGeneratorBuilder(long randomProviderSeed) {
            this.randomProviderSeed = randomProviderSeed;
        }

        LongGenerator.LongGeneratorBuilder min(long min) {
            this.min = min;
            return this;
        }

        LongGenerator.LongGeneratorBuilder max(long max) {
            this.max = max;
            return this;
        }

        LongGenerator.LongGeneratorBuilder ignore(List<Long> ignore) {
            this.ignore = ignore;
            return this;
        }

        LongGenerator.LongGeneratorBuilder ignore(long[] ignoreArray) {
            this.ignore = Arrays.stream(ignoreArray).boxed().collect(Collectors.toList());
            return this;
        }

        LongGenerator build() {
            return new LongGenerator(min, max, ignore, randomProviderSeed);
        }
    }

    @Override
    public String toString() {
        return "LongGenerator{" +
                "min=" + min +
                ", max=" + max +
                ", ignore=" + ignore +
                '}';
    }
}
