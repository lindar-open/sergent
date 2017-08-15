package com.lindar.sergent;

import org.apache.commons.rng.sampling.ListSampler;
import org.apache.commons.rng.sampling.PermutationSampler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Shuffler {
    long randomProviderId;

    private int start = 0;
    private boolean towardHead = false;

    Shuffler(int start, boolean towardHead, long randomProviderId) {
        this(randomProviderId);
        this.start = start;
        this.towardHead = towardHead;
    }

    public Shuffler(long randomProviderId) {
        this.randomProviderId = randomProviderId;
    }

    /**
     * If towardHead = false (default) it will shuffle all elements from this start index to end of list (right side),
     * otherwise it will shuffle the left side from the start index. Default index is 0
     */
    public Shuffler start(int start) {
        return buildCopy().start(start).build();
    }

    /**
     * If towardHead (default is false) it will shuffle all elements from the start index to end of list (right side),
     * otherwise it will shuffle the left side from the start index. Default index is 0
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
        ListSampler.shuffle(RandomProviderFactory.getInstance(this.randomProviderId), list, start, towardHead);
    }

    /**
     * Shuffle the entries of the given array, using the Fisher–Yates algorithm and the randomProvider chosen
     */
    public void array(int[] numbers) {
        PermutationSampler.shuffle(RandomProviderFactory.getInstance(this.randomProviderId), numbers, start, towardHead);
    }

    /**
     * Shuffle the entries of the given array, using the Fisher–Yates algorithm and the randomProvider chosen.
     * Returns a list of shuffled numbers from given array
     */
    public List<Integer> arrayToList(int[] numbers) {
        PermutationSampler.shuffle(RandomProviderFactory.getInstance(this.randomProviderId), numbers, start, towardHead);
        return Arrays.stream(numbers).boxed().collect(Collectors.toList());
    }

    private ShufflerBuilder buildCopy() {
        return new ShufflerBuilder(this.randomProviderId).start(this.start).towardHead(this.towardHead);
    }

    static class ShufflerBuilder {
        private long randomProviderId;

        private int start;
        private boolean towardHead;

        ShufflerBuilder(long randomProviderId) {
            this.randomProviderId = randomProviderId;
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
            return new Shuffler(start, towardHead, randomProviderId);
        }
    }
}
