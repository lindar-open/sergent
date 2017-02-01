package org.spauny.joy.sergent.impl;

import java.util.Random;

public class LiteGenerator extends Random implements RandomGenerator {

    private static final long serialVersionUID = -7481376607831660482L;
    
    public LiteGenerator() {
        super();
    }
    
    LiteGenerator(long seed) {
        super(seed);
    }

}
