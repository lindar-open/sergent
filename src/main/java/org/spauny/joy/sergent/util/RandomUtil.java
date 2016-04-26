package org.spauny.joy.sergent.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import org.apache.commons.lang3.ArrayUtils;

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

    public static int secureRandInt(int min, int max) {
        return secureRandInt(min, max, new ArrayList<Integer>());
    }

    public static int secureRandInt(int min, int max, int... ignore) {
        SecureRandom secRandom = buildSecureRandom();
        int randomNum = secRandom.nextInt((max - min) + 1) + min;
        if (ignore != null) {
            while (ArrayUtils.contains(ignore, randomNum)) {
                randomNum = secRandom.nextInt((max - min) + 1) + min;
            }
        }
        return randomNum;

    }

    public static int secureRandInt(int min, int max, List<Integer> ignore) {
        return secureRandInt(min, max, ignore, buildSecureRandom());

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

    public static List<Integer> secureRandIntList(int min, int max, boolean unique) {
        List<Integer> randomList = new ArrayList<>(max - min + 1);
        SecureRandom secRandom = buildSecureRandom();
        IntStream.rangeClosed(min, max).forEach(index -> {
            randomList.add(secureRandInt(min, max, randomList, secRandom));
        });
        return randomList;
    }
    
    public static List<Integer> secureRandIntList(int min, int max, int sequenceLimit, boolean unique) {
        List<Integer> randomList = new ArrayList<>(max - min + 1);
        SecureRandom secRandom = buildSecureRandom();
        IntStream.range(0, sequenceLimit).forEach(index -> {
            randomList.add(secureRandInt(min, max, randomList, secRandom));
        });
        return randomList;
    }

    private static SecureRandom buildSecureRandom() {
        SecureRandom secRandom;
        try {
            secRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RandomUtil.class.getName()).log(Level.SEVERE, null, ex);
            secRandom = new SecureRandom();
        }
        return secRandom;
    }

//    public static void main(String args[]) throws Exception {
//        System.out.println(mersenneRandIntList(1, 75, true));
//    }
    private RandomUtil() {
    }
}
