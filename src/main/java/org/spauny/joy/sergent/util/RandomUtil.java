package org.spauny.joy.sergent.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.apache.commons.lang3.ArrayUtils;
import org.uncommons.maths.number.NumberGenerator;
import org.uncommons.maths.random.DefaultSeedGenerator;
import org.uncommons.maths.random.DiscreteUniformGenerator;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.SeedException;
import org.uncommons.maths.random.SeedGenerator;

/**
 *
 * @author iulian.dafinoiu
 */
public class RandomUtil {

    public static int randInt(int min, int max) {
        return randInt(min, max, 0);
    }

    public static int randInt(int min, int max, long seed) {
        Random rand = createRandom(seed);
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }

    public static int randInt(int min, int max, int... ignore) {
        return randInt(min, max, 0, ignore);
    }

    public static int randInt(int min, int max, long seed, int... ignore) {
        Random rand = createRandom(seed);
        int randomNum = rand.nextInt((max - min) + 1) + min;
        while (ArrayUtils.contains(ignore, randomNum)) {
            randomNum = rand.nextInt((max - min) + 1) + min;
        }
        return randomNum;
    }

    private static Random createRandom(long seed) {
        Random rand;
        if (seed != 0) {
            rand = new Random(seed);
        } else {
            rand = new Random();
        }
        return rand;
    }

    public static int secureRandInt(int min, int max) throws NoSuchAlgorithmException {
        return secureRandInt(min, max, new ArrayList<Integer>());
    }

    public static int secureRandInt(int min, int max, int... ignore) throws NoSuchAlgorithmException {
        SecureRandom secRandom = SecureRandom.getInstance("SHA1PRNG");
        int randomNum = secRandom.nextInt((max - min) + 1) + min;
        if (ignore != null) {
            while (ArrayUtils.contains(ignore, randomNum)) {
                randomNum = secRandom.nextInt((max - min) + 1) + min;
            }
        }
        return randomNum;

    }

    public static int secureRandInt(int min, int max, List<Integer> ignore) throws NoSuchAlgorithmException {
        SecureRandom secRandom = SecureRandom.getInstance("SHA1PRNG");
        return secureRandInt(min, max, ignore, secRandom);

    }

    private static int secureRandInt(int min, int max, List<Integer> ignore, SecureRandom secRandom) {
        int randomNum = secRandom.nextInt((max - min) + 1) + min;
        if (ignore != null && !ignore.isEmpty()) {
            while (ignore.contains(randomNum)) {
                randomNum = secRandom.nextInt((max - min) + 1) + min;
            }
        }
        return randomNum;

    }

    public static List<Integer> secureRandIntList(int min, int max, boolean unique) throws NoSuchAlgorithmException {
        List<Integer> randomList = new ArrayList<>(max - min + 1);
        SecureRandom secRandom = SecureRandom.getInstance("SHA1PRNG");
        IntStream.rangeClosed(min, max).forEach(index -> {
            randomList.add(secureRandInt(min, max, randomList, secRandom));
        });
        return randomList;
    }

    public static int mersenneRandInt() throws SeedException {
        SeedGenerator seedGenerator = DefaultSeedGenerator.getInstance();
        return mersenneRandInt(seedGenerator);
    }

    public static int mersenneRandInt(SeedGenerator seedGenerator) throws SeedException {
        MersenneTwisterRNG mersenneTwisterRNG = new MersenneTwisterRNG(seedGenerator);
        return mersenneTwisterRNG.nextInt();
    }

    public static int mersenneRandInt(int min, int max) throws Exception {
        NumberGenerator<Integer> generator = new DiscreteUniformGenerator(min, max,
                new MersenneTwisterRNG());
        return mersenneRandInt(min, max, generator);
    }
    
    public static int mersenneRandInt(int min, int max, List<Integer> ignore) throws Exception {
        NumberGenerator<Integer> generator = new DiscreteUniformGenerator(min, max,
                new MersenneTwisterRNG());
        int value = mersenneRandInt(min, max, generator);
        while(!ignore.isEmpty() && ignore.contains(value)) {
            value = mersenneRandInt(min, max, generator);
        }
        return value;
    }

    public static int mersenneRandInt(int min, int max, NumberGenerator<Integer> numberGenerator) throws Exception {
        return numberGenerator.nextValue();
    }

    public static List<Integer> mersenneRandIntList(int min, int max, boolean unique) throws Exception {
        List<Integer> randomList = new ArrayList<>(max - min + 1);
        for (int i = min; i <= max; i++) {
            int genValue = mersenneRandInt(min, max);
            while (unique && randomList.contains(genValue)) {
                genValue = mersenneRandInt(min, max);
            }
            randomList.add(genValue);
        }

        return randomList;
    }

//    public static void main(String args[]) throws Exception {
//        System.out.println(mersenneRandIntList(1, 75, true));
//    }

    private RandomUtil() {
    }
}
