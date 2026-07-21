package com.riserdisk.dynamicservericon.config;

import com.riserdisk.dynamicservericon.Dynamicservericon;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private final Path configDirectory;
    private final Path iconsDirectory;

    public ConfigManager() {

        Path configRoot = FabricLoader.getInstance().getConfigDir();

        this.configDirectory = configRoot.resolve(Dynamicservericon.MOD_ID);
        this.iconsDirectory = configDirectory.resolve("icons");
    }

    public boolean initialize() {

        try {

            Files.createDirectories(configDirectory);
            Files.createDirectories(iconsDirectory);

            Dynamicservericon.LOGGER.info("Configuration initialized.");
            Dynamicservericon.LOGGER.info("Config folder: {}", configDirectory);
            Dynamicservericon.LOGGER.info("Icons folder: {}", iconsDirectory);
            return true;
            } 

        catch (IOException e) {
            Dynamicservericon.LOGGER.error("Failed to initialize configuration folders.", e);
            return false;
        }
    }

    public Path getConfigDirectory() {
        return configDirectory;
    }

    public Path getIconsDirectory() {
        return iconsDirectory;
    }

}