package com.lindar.sergent;

public class Sergent {

    public Sergent() {
    }

    /**
     * NOTE: Use this method to generate random single integers
     */
    public IntGenerator intGenerator() {
        return new IntGenerator();
    }

    /**
     * NOTE: Use this method to generate random single integers
     */
    public LongGenerator longGenerator() {
        return new LongGenerator();
    }

    /**
     * NOTE: Use this method to generate random strings
     */
    public StringGenerator stringGenerator() {
        return new StringGenerator();
    }

    /**
     * NOTE: Use this method to generate a random list of numbers (unique or not)
     */
    public ListGenerator listGenerator() {
        return new ListGenerator();
    }

    /**
     * NOTE: Use this method to shuffle lists or arrays
     */
    public Shuffler shuffle() {
        return new Shuffler();
    }

}
