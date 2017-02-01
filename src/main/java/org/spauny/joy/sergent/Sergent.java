package org.spauny.joy.sergent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.apache.commons.lang3.ArrayUtils;
import org.spauny.joy.sergent.impl.LiteGenerator;
import org.spauny.joy.sergent.impl.RandomGenerator;
import org.spauny.joy.sergent.util.SequenceProps;

public class Sergent {

    private RandomGenerator generator;

    /**
     * Returns an instance of java.util.Random. For more random generator implementations use SergentFactory to
     * initialise Sergent! NOTE: Do not use for anything sensitive!
     */
    public Sergent() {
        this.generator = new LiteGenerator();
    }

    public Sergent(RandomGenerator randomGenerator) {
        this.generator = randomGenerator;
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

    public List<Integer> randIntList(int min, int max, int listSize, boolean unique) {
        return randIntList(new SequenceProps().min(min).max(max).size(listSize).unique(unique));
    }

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

//    public static void main(String[] args) {
//        Sergent rng = SergentFactory.getISAACInstance();
//
//        long start = System.currentTimeMillis();
//
//        rng.randIntList(1, 75, 10_000_000, false);
//
//        long time = (System.currentTimeMillis() - start);
//
//        System.out.println("Time taken: " + time + "ms");
//    }

}
