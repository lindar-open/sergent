package com.lindar.sergent;

import org.apache.commons.rng.simple.internal.SeedFactory;

public class Sergent {

    long randomProviderSeed;

    public Sergent() {
        randomProviderSeed = SeedFactory.createLong();
    }

    public Sergent(long seed) {
        randomProviderSeed = seed;
    }

    /**
     * NOTE: Use this method to generate random single integers
     */
    public IntGenerator intGenerator() {
        return new IntGenerator(this.randomProviderSeed);
    }

    /**
     * NOTE: Use this method to generate random single integers
     */
    public LongGenerator longGenerator() {
        return new LongGenerator(this.randomProviderSeed);
    }

    /**
     * NOTE: Use this method to generate random strings
     */
    public StringGenerator stringGenerator() {
        return new StringGenerator(this.randomProviderSeed);
    }

    /**
     * NOTE: Use this method to generate a random list of numbers (unique or not)
     */
    public ListGenerator listGenerator() {
        return new ListGenerator(this.randomProviderSeed);
    }

    /**
     * NOTE: Use this method to shuffle lists or arrays
     */
    public Shuffler shuffle() {
        return new Shuffler(this.randomProviderSeed);
    }

}
