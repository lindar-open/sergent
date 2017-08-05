package com.lindar.sergent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListGenerator {

    private final IntGenerator intGenerator;
    private final Shuffler shuffler;

    ListGenerator(IntGenerator intGenerator, Shuffler shuffler) {
        this.intGenerator = intGenerator;
        this.shuffler = shuffler;
    }

    private int min;
    private int max;
    private int listSize;
    private boolean unique;

    public ListGenerator withMinAndMax(int min, int max) {
        if (min <= 0) throw new IllegalArgumentException("Min has to be positive and greater than 0. Use the withMax method when you want min = 0");
        this.min = min;
        return withMax(max);
    }

    public ListGenerator withMax(int max) {
        if (max <= 0) throw new IllegalArgumentException("Max has to be positive and greater than 0");
        this.max = max;
        return this;
    }

    public ListGenerator ofSize(int listSize) {
        if (listSize <= 0) throw new IllegalArgumentException("List size has to be positive and greater than 0");
        this.listSize = listSize;
        return this;
    }

    public ListGenerator unique() {
        this.unique = true;
        return this;
    }

    /**
     * This method generates a random list of integers between min and max. <br/>
     * NOTE: If not unique and list size != max-min there is no guarantee the returned list is uniform (every integer number between min and max)
     */
    public List<Integer> randIntegers() {

        if (unique && (listSize == 0 || listSize == max - min)) {
            List<Integer> numbers = IntStream.rangeClosed(min, max).boxed().collect(Collectors.toList());
            this.shuffler.list(numbers);
            return numbers;
        }

        int diff = max - min + 1;
        List<Integer> randomList = new ArrayList<>(listSize);
        IntStream.range(0, listSize).forEach(index -> {
            IntGenerator intGen = this.intGenerator.withMinAndMax(min, max);
            if (unique && diff >= listSize) {
                intGen.ignore(randomList);
            }
            randomList.add(intGen.randInt());
        });

        return randomList;
    }
}
