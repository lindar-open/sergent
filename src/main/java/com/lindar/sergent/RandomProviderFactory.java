package com.lindar.sergent;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RandomProviderFactory {
    private static final Map<Long, UniformRandomProvider> randomProviderHolder = new ConcurrentHashMap<>();

    private static final Map<Long, Integer> randomProviderAccessCounter = new ConcurrentHashMap<>();

    private static RandomSource getRandomSource() {
        RandomSource randomSource;
        try {
            randomSource = RandomSource.valueOf(SergentConfigs.INSTANCE.getRandomProviderDefaultImpl());
        } catch (Exception ex) {
            randomSource = RandomSource.MT;
        }
        return randomSource;
    }

    public static UniformRandomProvider getInstance(long randomProviderId) {
        RandomSource randomSource = getRandomSource();
        if (randomProviderHolder.containsKey(randomProviderId)) {
            reinitProviderIfNecessary(randomProviderId);
            return randomProviderHolder.get(randomProviderId);
        }
        UniformRandomProvider randomProvider = RandomSource.create(randomSource, SergentConfigs.INSTANCE.getRandomProviderSeed());
        randomProviderHolder.put(randomProviderId, randomProvider);
        return randomProvider;
    }

    static void incrementAccess(long randomProviderId) {
        Integer counter = randomProviderAccessCounter.get(randomProviderId);
        if (counter == null) counter = 0;
        randomProviderAccessCounter.put(randomProviderId, ++counter);
    }

    private static void reinitProviderIfNecessary(long randomProviderId) {
        Integer counter = randomProviderAccessCounter.get(randomProviderId);
        Integer maxGenerations = SergentConfigs.INSTANCE.getRandomProviderMaxGenerations();
        if (counter != null && counter > maxGenerations) {
            System.out.println("Max counter reached: " + maxGenerations + ". Random provider with ID: " + randomProviderId + " is being reinitialized");
            randomProviderHolder.put(randomProviderId, RandomSource.create(getRandomSource()));
            randomProviderAccessCounter.put(randomProviderId, 0);
        }

    }
}
