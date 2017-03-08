package lindar.sergent.impl;

import java.util.Random;

public class LiteGenerator extends Random implements RandomGenerator {

    private static final long serialVersionUID = -7481376607831660482L;
    
    public LiteGenerator() {
        super();
    }
    
    LiteGenerator(long seed) {
        super(seed);
    }

    @Override
    public long nextLong(long bound) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
