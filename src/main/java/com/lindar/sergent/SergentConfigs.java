package com.lindar.sergent;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.ClasspathLocationStrategy;
import org.apache.commons.configuration2.sync.ReadWriteSynchronizer;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

enum SergentConfigs {
    INSTANCE;

    private static final String CONFIGS_NAME = "sergent-configs.properties";

    private String randomProviderDefaultImpl;
    private Object randomProviderSeed;
    private int randomProviderMaxGenerations;

    private boolean backgroundCyclingEnabled;
    private int backgroundCyclingMinSkipCounter;
    private int backgroundCyclingMaxSkipCounter;

    SergentConfigs() {
        loadConfigs();
    }

    private void loadConfigs() {
        File propertiesFile = new File(CONFIGS_NAME);
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<PropertiesConfiguration> builder = new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                        .configure(params.fileBased().setSynchronizer(new ReadWriteSynchronizer()).setFile(propertiesFile).setLocationStrategy(new ClasspathLocationStrategy()));
        PropertiesConfiguration config;
        try {
            config = builder.getConfiguration();
            randomProviderDefaultImpl = config.getString("sergent.random-provider.default-implementation", "MT");
            if (StringUtils.isNotBlank(config.getString("sergent.random-provider.seed"))) {
                randomProviderSeed = config.getLong("sergent.random-provider.seed", null);
            }
            randomProviderMaxGenerations = config.getInt("sergent.random-provider.max-generations", 10_000);

            backgroundCyclingEnabled = config.getBoolean("sergent.background-cycling.enabled", false);
            backgroundCyclingMinSkipCounter = config.getInt("sergent.background-cycling.min-skip-counter", 10);
            backgroundCyclingMaxSkipCounter = config.getInt("sergent.background-cycling.max-skip-counter", 30);
        } catch (ConfigurationException e) {
            System.err.println("Could not load Sergent config file. " + e.getMessage());
        }
    }

    String getRandomProviderDefaultImpl() {
        return randomProviderDefaultImpl;
    }

    Object getRandomProviderSeed() {
        return randomProviderSeed;
    }

    void setRandomProviderSeed(Object randomProviderSeed) {
        this.randomProviderSeed = randomProviderSeed;
    }

    int getRandomProviderMaxGenerations() {
        return randomProviderMaxGenerations;
    }

    boolean isBackgroundCyclingEnabled() {
        return backgroundCyclingEnabled;
    }

    int getBackgroundCyclingMaxSkipCounter() {
        return backgroundCyclingMaxSkipCounter;
    }

    int getBackgroundCyclingMinSkipCounter() {
        return backgroundCyclingMinSkipCounter;
    }
}
