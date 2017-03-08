package lindar.sergent.impl;

import org.apache.commons.math3.random.MersenneTwister;

public class MersenneGenerator extends MersenneTwister implements RandomGenerator {

    private static final long serialVersionUID = -7891316427014273167L;
    
    MersenneGenerator() {
        super();
    }
    
    MersenneGenerator(int[] seed) {
        super(seed);
    }
    
    MersenneGenerator(long seed) {
        super(seed);
    }
}
