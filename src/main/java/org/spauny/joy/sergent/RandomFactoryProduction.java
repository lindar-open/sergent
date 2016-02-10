package org.spauny.joy.sergent;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author iulian.dafinoiu
 */
public class RandomFactoryProduction extends RandomFactory {
    @Override
    protected Random createRandom(boolean secure) {
        if (secure) {
            try {
                return new RNG();
            } catch (IOException | NoSuchAlgorithmException e) {
                throw new RandomFactoryException("Error generating random object", e);
            }
        } else {
            return new Random();
        }
    }
}
