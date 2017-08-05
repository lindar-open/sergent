package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

public class Sergent {

    private final IntGenerator intGenerator;
    private final LongGenerator longGenerator;
    private final StringGenerator stringGenerator;

    private final Shuffler shuffler;
    private final ListGenerator listGenerator;

    /**
     * Returns an instance of java.util.Random.
     * For more random randomProvider implementations use SergentFactory to initialise Sergent!
     */
    public Sergent() {
        this(RandomSource.create(RandomSource.MT));
    }

    public Sergent(UniformRandomProvider randomProvider) {
        intGenerator = new IntGenerator(randomProvider);
        longGenerator = new LongGenerator(randomProvider);
        stringGenerator = new StringGenerator(randomProvider);
        shuffler = new Shuffler(randomProvider);

        listGenerator = new ListGenerator(intGenerator, shuffler);
    }

    /**
     * NOTE: Use this method to generate random single integers
     */
    public IntGenerator intGenerator() {
        return this.intGenerator;
    }

    /**
     * NOTE: Use this method to generate random single integers
     */
    public LongGenerator longGenerator() {
        return this.longGenerator;
    }

    /**
     * NOTE: Use this method to generate random strings
     */
    public StringGenerator stringGenerator() {
        return this.stringGenerator;
    }

    /**
     * NOTE: Use this method to generate a random list of numbers (unique or not)
     */
    public ListGenerator listGenerator() {
        return this.listGenerator;
    }

    /**
     * NOTE: Use this method to shuffle lists or arrays
     */
    public Shuffler shuffle() {
        return this.shuffler;
    }

}
