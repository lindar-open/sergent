package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

public class RandomProviderFactory {

    private static RandomSource getRandomSource() {
        RandomSource randomSource;
        try {
            randomSource = RandomSource.valueOf(SergentConfigs.INSTANCE.getRandomProviderDefaultImpl());
        } catch (Exception ex) {
            randomSource = RandomSource.MT;
        }
        return randomSource;
    }

    public static UniformRandomProvider getInstance(long randomProviderSeed) {
        return RandomSource.create(getRandomSource(), randomProviderSeed);
    }
}
