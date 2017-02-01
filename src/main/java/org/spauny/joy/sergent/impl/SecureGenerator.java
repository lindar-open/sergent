package org.spauny.joy.sergent.impl;

import java.security.SecureRandom;

public class SecureGenerator extends SecureRandom implements RandomGenerator {

    private static final long serialVersionUID = -4128805743117958170L;
    
    SecureGenerator() {
        super();
    }
    
    SecureGenerator(byte[] seed) {
        super(seed);
    }

}
