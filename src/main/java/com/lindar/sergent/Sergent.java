package com.lindar.sergent;

import com.lindar.sergent.util.SequenceProps;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.MathArrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Sergent {

    private final RandomGenerator generator;

    /**
     * Returns an instance of java.util.Random. 
     * For more random generator implementations use SergentFactory to initialise Sergent!
     */
    public Sergent() {
        this.generator = new MersenneTwister();
    }

    public Sergent(RandomGenerator randomGenerator) {
        this.generator = randomGenerator;
    }

    public long randLong() {
        return generator.nextLong();
    }

    public int randInt() {
        return randInt(0, Integer.MAX_VALUE, new ArrayList<>(0));
    }

    public int randInt(int min, int max) {
        return randInt(min, max, new ArrayList<>(0));
    }

    public int randInt(int min, int max, int... ignore) {
        int randomNum = generator.nextInt((max - min) + 1) + min;
        if (ignore != null) {
            while (ArrayUtils.contains(ignore, randomNum)) {
                randomNum = generator.nextInt((max - min) + 1) + min;
            }
        }
        return randomNum;

    }

    public int randInt(int min, int max, List<Integer> ignore) {
        int randomNum = generator.nextInt((max - min) + 1) + min;
        if (ignore != null && !ignore.isEmpty()) {
            while (ignore.contains(randomNum)) {
                randomNum = generator.nextInt((max - min) + 1) + min;
            }
        }
        return randomNum;

    }

    /**
     * Shuffle the entries of the given list, using the Fisher–Yates algorithm and the random generator implementation chosen
     */
    public List<Integer> shuffleList(List<Integer> numbers) {
        int[] numbersArray = numbers.stream().mapToInt(i->i).toArray();
        MathArrays.shuffle(numbersArray, this.generator);
        return Arrays.stream(numbersArray).boxed().collect(Collectors.toList());
    }

    /**
     * Shuffle the entries of the given array, using the Fisher–Yates algorithm and the random generator implementation chosen
     */
    public void shuffleArray(int[] numbers) {
        MathArrays.shuffle(numbers, this.generator);
    }

    /**
     * Shuffle the entries of the given array, using the Fisher–Yates algorithm and the random generator implementation chosen.
     * Returns a list of shuffled numbers from given array
     */
    public List<Integer> shuffleArrayToList(int[] numbers) {
        MathArrays.shuffle(numbers, this.generator);
        return Arrays.stream(numbers).boxed().collect(Collectors.toList());
    }

    /**
     * This method generates a unique (no duplicates) random integer list starting from 1 to [max] (step=1)
     */
    public List<Integer> uniformSequence(int max) {
        return uniformSequence(1, max);
    }

    /**
     * This method generates a unique (no duplicates) random integer list starting from 0 to [max] (step=1)
     */
    public List<Integer> uniformSequenceFrom0(int max) {
        return uniformSequence(0, max);
    }

    /**
     * This method generates a unique (no duplicates) random integer list starting from min to [max] (step=1)
     */
    public List<Integer> uniformSequence(int min, int max) {
        int[] numbers = IntStream.rangeClosed(min, max).toArray();
        MathArrays.shuffle(numbers, this.generator);
        return Arrays.stream(numbers).boxed().collect(Collectors.toList());
    }

    /**
     * This method generates a random list of integers between min and max. <br/>
     * NOTE: If listSize = max-min there is no guarantee the returned list is uniform (every integer number between min and max) <br/>
     * Use {Sergent#uniformSequence} if the listSize = max-min
     */
    public List<Integer> randIntList(int min, int max, int listSize, boolean unique) {
        return randIntList(new SequenceProps().min(min).max(max).size(listSize).unique(unique));
    }

    /**
     * This method generates a random list of integers between min and max. <br/>
     * NOTE: If list size = max-min there is no guarantee the returned list is uniform (every integer number between min and max) <br/>
     * Use {Sergent#uniformSequence} if the list size = max-min
     */
    public List<Integer> randIntList(SequenceProps sequenceProps) {
        int size = sequenceProps.size();
        int min = sequenceProps.min();
        int max = sequenceProps.max();
        boolean unique = sequenceProps.unique();
        int diff = max - min + 1;
        List<Integer> randomList = new ArrayList<>(size);
        IntStream.range(0, size).forEach(index -> {
            int randInt;
            if (unique && diff >= size) {
                randInt = randInt(min, max, randomList);
            } else {
                randInt = randInt(min, max);
            }
            randomList.add(randInt);
        });

        return randomList;
    }

    public static void main(String[] args) {
        Sergent rng = SergentFactory.getInstance();

        long start = System.currentTimeMillis();

//        rng.randIntList(1, 75, 10_000_000, false);
        List<Integer> seq = rng.uniformSequence(10_000);
//        System.out.println("seq: " + seq);

        long time = (System.currentTimeMillis() - start);

        System.out.println("Time taken: " + time + "ms");
    }

}
