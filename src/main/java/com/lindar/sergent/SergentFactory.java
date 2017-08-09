package com.lindar.sergent;

public final class SergentFactory {
    
    public static Sergent newInstance() {
        return new Sergent();
    }

    public static Sergent newInstance(long seed) {
        SergentConfigs.INSTANCE.setRandomProviderSeed(seed);
        return new Sergent();
    }

    public static Sergent newInstance(int seed) {
        SergentConfigs.INSTANCE.setRandomProviderSeed(seed);
        return new Sergent();
    }
    
    private SergentFactory() {
    }

}
