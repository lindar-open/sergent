package com.lindar.sergent;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.ClasspathLocationStrategy;
import org.apache.commons.configuration2.sync.ReadWriteSynchronizer;

import java.io.File;

enum SergentConfigs {
    INSTANCE;

    private static final String CONFIGS_NAME = "sergent-configs.properties";

    private String randomProviderDefaultImpl;
    private int randomProviderMaxGenerations;
    private boolean randomProviderManageInstances;

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
        PropertiesConfiguration config = new PropertiesConfiguration();
        try {
            config = builder.getConfiguration();
        } catch (ConfigurationException e) {
            System.err.println("Could not load Sergent config file. " + e.getMessage());
        }

        randomProviderDefaultImpl = config.getString("sergent.random-provider.default-implementation", "MT");
        randomProviderMaxGenerations = config.getInt("sergent.random-provider.max-generations", 10_000);
        randomProviderManageInstances = config.getBoolean("sergent.random-provider.controlled-reseeding-enabled", true);

        backgroundCyclingEnabled = config.getBoolean("sergent.background-cycling.enabled", false);
        backgroundCyclingMinSkipCounter = config.getInt("sergent.background-cycling.min-skip-counter", 10);
        backgroundCyclingMaxSkipCounter = config.getInt("sergent.background-cycling.max-skip-counter", 30);

        System.out.println("Loaded Sergent configs: " + this.toString());
    }

    String getRandomProviderDefaultImpl() {
        return randomProviderDefaultImpl;
    }

    int getRandomProviderMaxGenerations() {
        return randomProviderMaxGenerations;
    }

    boolean getRandomProviderManageInstances() {
        return randomProviderManageInstances;
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

    @Override
    public String toString() {
        return "SergentConfigs{" + "randomProviderDefaultImpl='" + randomProviderDefaultImpl + '\'' +
                ", randomProviderMaxGenerations=" + randomProviderMaxGenerations +
                ", backgroundCyclingEnabled=" + backgroundCyclingEnabled +
                ", backgroundCyclingMinSkipCounter=" + backgroundCyclingMinSkipCounter +
                ", backgroundCyclingMaxSkipCounter=" + backgroundCyclingMaxSkipCounter +
                '}';
    }
}
