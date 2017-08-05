package com.lindar.sergent;

import org.apache.commons.rng.simple.RandomSource;

public final class SergentFactory {
    
    /**
     * Uses the strongest instance of a random provider available.
     * At the moment it will use an instance of {@link org.apache.commons.rng.core.source32.ISAACRandom} but the implementation may change.
     * If you want a guaranteed {@link org.apache.commons.rng.core.source32.ISAACRandom} every time, use {@link SergentFactory#getISAACInstance} method.
     */
    public static Sergent getInstance() {
        return new Sergent(RandomSource.create(RandomSource.ISAAC));
    }

    /**
     * Uses the strongest instance of a random provider available.
     * At the moment it will use an instance of {@link org.apache.commons.rng.core.source32.ISAACRandom} but the implementation may change.
     * If you want a guaranteed {@link org.apache.commons.rng.core.source32.ISAACRandom} every time, use {@link SergentFactory#getISAACInstance} method.
     */
    public static Sergent getInstance(long seed) {
        return new Sergent(RandomSource.create(RandomSource.ISAAC, seed));
    }

    /**
     * Uses the strongest instance of a random provider available.
     * At the moment it will use an instance of {@link org.apache.commons.rng.core.source32.ISAACRandom} but the implementation may change.
     * If you want a guaranteed {@link org.apache.commons.rng.core.source32.ISAACRandom} every time, use {@link SergentFactory#getISAACInstance} method.
     */
    public static Sergent getInstance(int seed) {
        return new Sergent(RandomSource.create(RandomSource.ISAAC, seed));
    }
    
    /**
     * Returns an instance of {@link org.apache.commons.rng.core.source32.MersenneTwister}
     * The instance is initialized using the current time plus the system identity hash code of this instance as the seed
     */
    public static Sergent getMersenneInstance() {
        return new Sergent(RandomSource.create(RandomSource.MT));
    }

    /**
     * Returns an instance of {@link org.apache.commons.rng.core.source64.MersenneTwister64}
     * - the 64-bits version of the originally 32-bits {@link org.apache.commons.rng.core.source32.MersenneTwister Mersenne Twister}
     * The instance is initialized using the current time plus the system identity hash code of this instance as the seed
     */
    public static Sergent getMersenneInstance64() {
        return new Sergent(RandomSource.create(RandomSource.MT_64));
    }

    /**
     * Returns an instance of {@link org.apache.commons.rng.core.source32.MersenneTwister}
     * @param seed
     */
    public static Sergent getMersenneInstance(int seed) {
        return new Sergent(RandomSource.create(RandomSource.MT, seed));
    }
    
    /**
     * Returns an instance of {@link org.apache.commons.rng.core.source64.MersenneTwister64}
     * - the 64-bits version of the originally 32-bits {@link org.apache.commons.rng.core.source32.MersenneTwister Mersenne Twister}
     * @param seed
     */
    public static Sergent getMersenneInstance(long seed) {
        return new Sergent(RandomSource.create(RandomSource.MT_64, seed));
    }
    
    /**
     * Uses an instance of {@link org.apache.commons.rng.core.source32.ISAACRandom}
     * The instance is initialized using the current time plus the system identity hash code of this instance as the seed
     */
    public static Sergent getISAACInstance() {
        return new Sergent(RandomSource.create(RandomSource.ISAAC));
    }
    
    /**
     * Uses an instance of {@link org.apache.commons.rng.core.source32.ISAACRandom}
     * @param seed
     */
    public static Sergent getISAACInstance(int seed) {
        return new Sergent(RandomSource.create(RandomSource.ISAAC, seed));
    }

    /**
     * Uses an instance of {@link org.apache.commons.rng.core.source32.Well19937c}
     * The instance is initialized using the current time as seed
     */
    public static Sergent getWell19937cInstance() {
        return new Sergent(RandomSource.create(RandomSource.WELL_19937_C));
    }

    /**
     * Uses an instance of {@link org.apache.commons.rng.core.source32.Well19937c}
     */
    public static Sergent getWell19937cInstance(int seed) {
        return new Sergent(RandomSource.create(RandomSource.WELL_19937_C, seed));
    }

    private SergentFactory() {
    }

}
