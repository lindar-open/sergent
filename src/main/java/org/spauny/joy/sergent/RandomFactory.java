package org.spauny.joy.sergent;

import java.util.Random;

/**
 *
 * @author iulian.dafinoiu
 */
public abstract class RandomFactory {
    private static RandomFactory factory = new RandomFactoryProduction();

    public static Random getRandomNumberGenerator(boolean secure) {
        return factory.createRandom(secure);
    }

    public static void setFactory(RandomFactory factory) {
        RandomFactory.factory = factory;
    }

    protected abstract Random createRandom(boolean secure);
}
