package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

public class Sergent {

    long randomProviderId;
    public Sergent() {
        UniformRandomProvider randomProvider = RandomSource.create(RandomSource.MT);
        randomProviderId = randomProvider.nextLong();
    }

    /**
     * NOTE: Use this method to generate random single integers
     */
    public IntGenerator intGenerator() {
        return new IntGenerator(this.randomProviderId);
    }

    /**
     * NOTE: Use this method to generate random single integers
     */
    public LongGenerator longGenerator() {
        return new LongGenerator(this.randomProviderId);
    }

    /**
     * NOTE: Use this method to generate random strings
     */
    public StringGenerator stringGenerator() {
        return new StringGenerator(this.randomProviderId);
    }

    /**
     * NOTE: Use this method to generate a random list of numbers (unique or not)
     */
    public ListGenerator listGenerator() {
        return new ListGenerator(this.randomProviderId);
    }

    /**
     * NOTE: Use this method to shuffle lists or arrays
     */
    public Shuffler shuffle() {
        return new Shuffler(this.randomProviderId);
    }

}
