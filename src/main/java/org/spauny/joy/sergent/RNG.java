/*
 * The PRNG used to resolve client bets.  Extends secure random and
 * reseeds itself when the reseed interval is reached as specified in
 * in the propeties file. Entropy is supplied by a large sound file
 * possibly of white noise where bits are read at random.
 */
package org.spauny.joy.sergent;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RNG extends SecureRandom {

    private int numberOfCalls = 0;
    private static int RESEED_INTERVAL = 500;

    static {
      RESEED_INTERVAL = 1000;
    }

    public RNG() throws IOException, NoSuchAlgorithmException {
        super();
        super.setSeed(new Seed().getSeed(8));
    }

    /*
     * Overides the default nextInt of SecureRandom.  Keeps a
     * count of the number of times the method ahs been called and
     * when a threshold is reached calls the reseed method.
     */
    @Override
    public synchronized int nextInt(int i) {

        numberOfCalls++;
        if (numberOfCalls == RESEED_INTERVAL) {
            numberOfCalls = 0;
            try {
                super.setSeed(new Seed().getSeed(8));
            } catch (Exception e) {
                log.error("reseed error", e);
            }
        }
        return super.nextInt(i);
    }

    public static void main(String[] args) {
        try {
            RNG rng = new RNG();
//            int[] result = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            int size = 5;
            long start = System.currentTimeMillis();
            for (int i = 0; i < 74; i++) {
                System.out.println(rng.nextInt(75));
            }
            long time = (System.currentTimeMillis() - start);
            System.out.println("Time taken: " + time + "ms");
        } catch (Exception e) {
            System.out.println("I love java: " + e);
        }
    }
}
