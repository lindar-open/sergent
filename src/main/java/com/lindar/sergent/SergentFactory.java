package com.lindar.sergent;

import org.apache.commons.math3.random.ISAACRandom;
import org.apache.commons.math3.random.MersenneTwister;
import org.apache.commons.math3.random.Well19937c;

public final class SergentFactory {
    
    /**
     * Return the strongest instance of a random generator available. 
     * At the moment it will return an instance of org.apache.commons.math3.random.ISAACRandom but the implementation may change.
     * If you want a guaranteed org.apache.commons.math3.random.ISAACRandom every time, use getISAACInstance method.
     */
    public static Sergent getInstance() {
        return new Sergent(new ISAACRandom());
    }
    
    /**
     * Return the strongest instance of a random generator available. 
     * At the moment it will return an instance of org.apache.commons.math3.random.ISAACRandom but the implementation may change.
     * If you want a guaranteed org.apache.commons.math3.random.ISAACRandom every time, use getISAACInstance method.
     * @param seed
     */
    public static Sergent getInstance(long seed) {
        return new Sergent(new ISAACRandom(seed));
    }
    
    /**
     * Returns an instance of org.apache.commons.math3.random.MersenneTwister.
     * The instance is initialized using the current time plus the system identity hash code of this instance as the seed
     */
    public static Sergent getMersenneInstance() {
        return new Sergent(new MersenneTwister());
    }
    
    /**
     * Returns an instance of org.apache.commons.math3.random.MersenneTwister.
     * @param seed
     */
    public static Sergent getMersenneInstance(long seed) {
        return new Sergent(new MersenneTwister(seed));
    }
    
    /**
     * Returns an instance of org.apache.commons.math3.random.ISAACRandom.
     * The instance is initialized using the current time plus the system identity hash code of this instance as the seed
     */
    public static Sergent getISAACInstance() {
        return new Sergent(new ISAACRandom());
    }
    
    /**
     * Returns an instance of org.apache.commons.math3.random.ISAACRandom.
     * @param seed
     */
    public static Sergent getISAACInstance(long seed) {
        return new Sergent(new ISAACRandom(seed));
    }

    /**
     * Returns an instance of org.apache.commons.math3.random.Well19937c.
     * The instance is initialized using the current time as seed
     */
    public static Sergent getWell19937cInstance() {
        return new Sergent(new Well19937c());
    }

    /**
     * Returns an instance of org.apache.commons.math3.random.Well19937c.
     */
    public static Sergent getWell19937cInstance(long seed) {
        return new Sergent(new Well19937c(seed));
    }

    private SergentFactory() {
    }

}
