package com.lindar.sergent;

import org.apache.commons.rng.sampling.ListSampler;
import org.apache.commons.rng.sampling.PermutationSampler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Shuffler {
    private Long randomProviderSeed;

    private int start = 0;
    private boolean towardHead = false;

    Shuffler(int start, boolean towardHead, Long randomProviderSeed) {
        this.randomProviderSeed = randomProviderSeed;
        this.start = start;
        this.towardHead = towardHead;
    }

    public Shuffler() {
    }

    public Shuffler withSeed(Long seed) {
        return buildCopy().seed(seed).build();
    }

    /**
     * If towardHead = false (default) it will shuffle all elements from this start index to end of list (right side),
     * otherwise it will shuffle the left side from the start index. Default index is 0
     */
    public Shuffler start(int start) {
        return buildCopy().start(start).build();
    }

    /**
     * If towardHead (default is false) it will shuffle all elements from the start index to beginning of list (left side),
     * otherwise it will shuffle the right side from the start index. Default index is 0
     */
    public Shuffler towardHead() {
        return buildCopy().towardHead(true).build();
    }

    /**
     * If towardTail (by default) it will shuffle all elements from the start index to end of list (right side),
     * otherwise it will shuffle the left side from the start index. Default index is 0
     */
    public Shuffler towardTail() {
        return buildCopy().towardHead(false).build();
    }


    /**
     * Shuffle the entries of the given list, using the Fisher–Yates algorithm and the randomProvider chosen
     */
    public <T> void list(List<T> list) {
        ListSampler.shuffle(RandomProviderFactory.getInstance(this.randomProviderSeed), list, start, towardHead);
    }

    /**
     * Shuffle the entries of the given array, using the Fisher–Yates algorithm and the randomProvider chosen
     */
    public void array(int[] numbers) {
        PermutationSampler.shuffle(RandomProviderFactory.getInstance(this.randomProviderSeed), numbers, start, towardHead);
    }

    /**
     * Shuffle the entries of the given array, using the Fisher–Yates algorithm and the randomProvider chosen.
     * Returns a list of shuffled numbers from given array
     */
    public List<Integer> arrayToList(int[] numbers) {
        PermutationSampler.shuffle(RandomProviderFactory.getInstance(this.randomProviderSeed), numbers, start, towardHead);
        return Arrays.stream(numbers).boxed().collect(Collectors.toList());
    }

    private ShufflerBuilder buildCopy() {
        return new ShufflerBuilder().seed(this.randomProviderSeed).start(this.start).towardHead(this.towardHead);
    }

    static class ShufflerBuilder {
        private Long randomProviderSeed;

        private int start;
        private boolean towardHead;

        Shuffler.ShufflerBuilder seed(Long randomProviderSeed) {
            this.randomProviderSeed = randomProviderSeed;
            return this;
        }

        Shuffler.ShufflerBuilder start(int start) {
            this.start = start;
            return this;
        }

        Shuffler.ShufflerBuilder towardHead(boolean towardHead) {
            this.towardHead = towardHead;
            return this;
        }

        Shuffler build() {
            return new Shuffler(start, towardHead, randomProviderSeed);
        }
    }
}
