package com.riserdisk.dynamicservericon.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.riserdisk.dynamicservericon.Dynamicservericon;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private final Path configDirectory;
    private final Path iconsDirectory;
    private final Path configFile;

    private ModConfig config;

    public ConfigManager() {

        Path configRoot = FabricLoader.getInstance().getConfigDir();

        this.configDirectory = configRoot.resolve(Dynamicservericon.MOD_ID);
        this.iconsDirectory = configDirectory.resolve("icons");
        this.configFile = configDirectory.resolve("config.json");

    }

    public boolean initialize() {

        try {

            Files.createDirectories(configDirectory);
            Files.createDirectories(iconsDirectory);

            loadConfig();

            Dynamicservericon.LOGGER.info("Configuration initialized.");
            Dynamicservericon.LOGGER.info("Config folder: {}", configDirectory);
            Dynamicservericon.LOGGER.info("Icons folder: {}", iconsDirectory);

            return true;

        } catch (IOException e) {

            Dynamicservericon.LOGGER.error(
                    "Failed to initialize configuration.",
                    e
            );

            return false;

        }

    }

    public ModConfig reload() {

    try {

        return loadConfig();

    } catch (IOException e) {

        Dynamicservericon.LOGGER.error(
                "Failed to reload configuration.",
                e
        );

        return config;

        }

    }

    public void save() {

        try {

            saveConfig();

        } catch (IOException e) {

            Dynamicservericon.LOGGER.error(
                    "Failed to save configuration.",
                    e
            );

        }

    }

    public void setRotationInterval(int seconds) {

        config.getRotation().setInterval(seconds);

        save();

    }

    private ModConfig loadConfig() throws IOException {

    if (Files.notExists(configFile)) {

        config = new ModConfig();

        saveConfig();

        Dynamicservericon.LOGGER.info(
                "Created default config.json"
        );

        return config;

    }

    try (Reader reader = Files.newBufferedReader(configFile)) {

        config = GSON.fromJson(reader, ModConfig.class);

    }

    if (config == null) {

        config = new ModConfig();

        saveConfig();

        Dynamicservericon.LOGGER.warn(
                "config.json was empty. Restored defaults."
        );

    }

    return config;

}

    private void saveConfig() throws IOException {

        try (Writer writer = Files.newBufferedWriter(configFile)) {

            GSON.toJson(config, writer);

        }

    }

    public ModConfig getConfig() {

        return config;

    }

    public Path getConfigDirectory() {

        return configDirectory;

    }

    public Path getIconsDirectory() {

        return iconsDirectory;

    }

}