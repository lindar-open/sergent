package com.lindar.sergent;

public final class SergentFactory {
    
    public static Sergent newInstance() {
        return new Sergent();
    }

    public static Sergent newInstance(long seed) {
        return new Sergent(seed);
    }

    private SergentFactory() {
    }

}
