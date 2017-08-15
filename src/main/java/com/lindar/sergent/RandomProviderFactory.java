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
            reinitProviderIfNecessary(randomSource);
            return randomProviderHolder.get(randomSource);
        }
        UniformRandomProvider randomProvider = RandomSource.create(randomSource, SergentConfigs.INSTANCE.getRandomProviderSeed());
        randomProviderHolder.put(randomSource, randomProvider);
        return randomProvider;
    }

    static void incrementAccess() {
        Integer counter = randomProviderAccessCounter.get(getRandomSource());
        if (counter == null) counter = 0;
        randomProviderAccessCounter.put(getRandomSource(), ++counter);
    }

    private static void reinitProviderIfNecessary(RandomSource randomSource) {
        Integer counter = randomProviderAccessCounter.get(randomSource);
        Integer maxGenerations = SergentConfigs.INSTANCE.getRandomProviderMaxGenerations();
        if (counter > maxGenerations) {
            System.out.println("Max counter reached: " + maxGenerations + ". Random provider is being reinitialized");
            randomProviderHolder.put(getRandomSource(), RandomSource.create(getRandomSource()));
            randomProviderAccessCounter.put(getRandomSource(), 0);
        }

    }
}
