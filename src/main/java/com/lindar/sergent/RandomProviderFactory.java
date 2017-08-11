package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RandomProviderFactory {
    private static final Map<RandomSource, UniformRandomProvider> randomProviderHolder = new ConcurrentHashMap<>();

    private static final Map<RandomSource, Integer> randomProviderAccessCounter = new ConcurrentHashMap<>();

    private static RandomSource getRandomSource() {
        RandomSource randomSource;
        try {
            randomSource = RandomSource.valueOf(SergentConfigs.INSTANCE.getRandomProviderDefaultImpl());
        } catch (Exception ex) {
            randomSource = RandomSource.MT;
        }
        return randomSource;
    }

    public static UniformRandomProvider getInstance() {
        RandomSource randomSource = getRandomSource();
        if (randomProviderHolder.containsKey(randomSource)) {
            return randomProviderHolder.get(randomSource);
        }
        UniformRandomProvider randomProvider = RandomSource.create(randomSource, SergentConfigs.INSTANCE.getRandomProviderSeed());
        randomProviderHolder.put(randomSource, randomProvider);
        return randomProvider;
    }

    static void incrementAccess() {
        Integer counter = randomProviderAccessCounter.get(getRandomSource());
        if (counter == null) counter = 0;
        else if (counter > SergentConfigs.INSTANCE.getRandomProviderMaxGenerations()) {
            System.out.println("Max counter reached: " + SergentConfigs.INSTANCE.getRandomProviderMaxGenerations()
                    + ". Random provider is being reinitialized");
            reinitProvider();
            counter = 0;
        }
        randomProviderAccessCounter.put(getRandomSource(), ++counter);
    }

    private static void reinitProvider() {
        randomProviderHolder.put(getRandomSource(), RandomSource.create(getRandomSource()));
    }
}
