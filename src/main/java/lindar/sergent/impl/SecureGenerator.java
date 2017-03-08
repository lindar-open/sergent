package lindar.sergent.impl;

import java.security.SecureRandom;

public class SecureGenerator extends SecureRandom implements RandomGenerator {

    private static final long serialVersionUID = -4128805743117958170L;
    
    SecureGenerator() {
        super();
    }
    
    SecureGenerator(byte[] seed) {
        super(seed);
    }

    @Override
    public long nextLong(long bound) {
        throw new UnsupportedOperationException("Next long with bound is not supported by SecureRandom");
    }

}
