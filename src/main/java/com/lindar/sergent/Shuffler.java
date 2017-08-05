package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.sampling.ListSampler;
import org.apache.commons.rng.sampling.PermutationSampler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Shuffler {

    private final UniformRandomProvider randomProvider;

    Shuffler(UniformRandomProvider randomProvider) {
        this.randomProvider = randomProvider;
    }

    private int start = 0;
    private boolean towardHead = false;

    /**
     * If towardHead = false (default) it will shuffle all elements from this start index to end of list (right side),
     * otherwise it will shuffle the left side from the start index. Default index is 0
     */
    public Shuffler start(int start) {
        this.start = start;
        return this;
    }

    /**
     * If towardHead (default is false) it will shuffle all elements from the start index to end of list (right side),
     * otherwise it will shuffle the left side from the start index. Default index is 0
     */
    public Shuffler towardHead() {
        this.towardHead = true;
        return this;
    }

    /**
     * If towardTail (by default) it will shuffle all elements from the start index to end of list (right side),
     * otherwise it will shuffle the left side from the start index. Default index is 0
     */
    public Shuffler towardTail() {
        this.towardHead = false;
        return this;
    }


    /**
     * Shuffle the entries of the given list, using the Fisher–Yates algorithm and the randomProvider chosen
     */
    public <T> void list(List<T> list) {
        ListSampler.shuffle(this.randomProvider, list, start, towardHead);
    }

    /**
     * Shuffle the entries of the given array, using the Fisher–Yates algorithm and the randomProvider chosen
     */
    public void array(int[] numbers) {
        PermutationSampler.shuffle(this.randomProvider, numbers, start, towardHead);
    }

    /**
     * Shuffle the entries of the given array, using the Fisher–Yates algorithm and the randomProvider chosen.
     * Returns a list of shuffled numbers from given array
     */
    public List<Integer> arrayToList(int[] numbers) {
        PermutationSampler.shuffle(this.randomProvider, numbers, start, towardHead);
        return Arrays.stream(numbers).boxed().collect(Collectors.toList());
    }
}
