package com.lindar.sergent.impl;

import com.lindar.sergent.Sergent;

public final class SergentFactory {
    
    /**
     * Return the strongest instance of a random generator available. 
     * At the moment it will return an instance of java.security.SecureRandom but the implementation may change. 
     * If you want a guaranteed java.security.SecureRandom every time, use getSecureInstance method.
     * @return
     */
    public static Sergent getInstance() {
        return new Sergent(new MersenneGenerator());
    }
    
    /**
     * Return the strongest instance of a random generator available. 
     * At the moment it will return an instance of java.security.SecureRandom but the implementation may change. 
     * If you want a guaranteed java.security.SecureRandom every time, use getSecureInstance method.
     * @param seed
     * @return
     */
    public static Sergent getInstance(long seed) {
        return new Sergent(new MersenneGenerator(seed));
    }
    
    /**
     * Returns an instance of java.util.Random. 
     * @return
     */
    public static Sergent getLiteInstance() {
        return new Sergent(new LiteGenerator());
    }
    
    /**
     * Returns an instance of java.util.Random. 
     * @param seed
     * @return
     */
    public static Sergent getLiteInstance(long seed) {
        return new Sergent(new LiteGenerator(seed));
    }
    
    /**
     * Returns an instance of java.security.SecureRandom. 
     * @return
     */
    public static Sergent getSecureInstance() {
        return new Sergent(new SecureGenerator());
    }
    
    /**
     * Returns an instance of java.security.SecureRandom. 
     * @param seed
     * @return
     */
    public static Sergent getSecureInstance(long seed) {
        SecureGenerator secureGenerator = new SecureGenerator();
        secureGenerator.setSeed(seed);
        return new Sergent(secureGenerator);
    }
    
    /**
     * Returns an instance of org.apache.commons.math3.random.MersenneTwister.
     * The instance is initialized using the current time plus the system identity hash code of this instance as the seed
     * @return
     */
    public static Sergent getMersenneInstance() {
        return new Sergent(new MersenneGenerator());
    }
    
    /**
     * Returns an instance of org.apache.commons.math3.random.MersenneTwister.
     * @param seed
     * @return
     */
    public static Sergent getMersenneInstance(long seed) {
        return new Sergent(new MersenneGenerator(seed));
    }
    
    /**
     * Returns an instance of org.apache.commons.math3.random.ISAACRandom.
     * The instance is initialized using the current time plus the system identity hash code of this instance as the seed
     * @return
     */
    public static Sergent getISAACInstance() {
        return new Sergent(new ISAACGenerator());
    }
    
    /**
     * Returns an instance of org.apache.commons.math3.random.ISAACRandom.
     * @param seed
     * @return
     */
    public static Sergent getISAACInstance(long seed) {
        return new Sergent(new ISAACGenerator(seed));
    }

    private SergentFactory() {
    }

}
