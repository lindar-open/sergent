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
     * NOTE: Use this method to generate a random list of numbers (unique or not)
     */
    public ListGenerator listGenerator() {
        return new ListGenerator();
    }

    public Shuffler shuffle() {
        return new Shuffler();
    }

}
