package org.spauny.joy.sergent.impl;

import org.apache.commons.math3.random.ISAACRandom;

public class ISAACGenerator extends ISAACRandom implements RandomGenerator {

    private static final long serialVersionUID = -7644930040824583240L;

    ISAACGenerator() {
        super();
    }
    
    ISAACGenerator(int[] seed) {
        super(seed);
    }
    
    ISAACGenerator(long seed) {
        super(seed);
    }
}
