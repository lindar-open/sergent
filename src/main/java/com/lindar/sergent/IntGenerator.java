package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class IntGenerator {

    private int min = Integer.MIN_VALUE;
    private int max = Integer.MAX_VALUE - 1;
    private List<Integer> ignore = Collections.emptyList();

    IntGenerator(int min, int max, List<Integer> ignoreList) {
        this.min = min;
        this.max = max;
        this.ignore = ignoreList;

        if(this.ignore == null){
            this.ignore = Collections.emptyList();
        } else {
            Collections.sort(ignoreList);
        }
    }

    public IntGenerator() {
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

    public int randInt(){
        UniformRandomProvider randomProvider = RandomProviderFactory.getInstance();

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
        IntGeneratorBuilder generatorBuilder = new IntGeneratorBuilder().min(this.min).max(this.max);
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

        IntGeneratorBuilder() {
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
            return new IntGenerator(min, max, ignore);
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
