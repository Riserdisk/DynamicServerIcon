package com.riserdisk.dynamicservericon;

import com.riserdisk.dynamicservericon.command.DynamicServerIconCommand;
import com.riserdisk.dynamicservericon.config.ConfigManager;
import com.riserdisk.dynamicservericon.config.ModConfig;
import com.riserdisk.dynamicservericon.icons.IconCache;
import com.riserdisk.dynamicservericon.icons.IconLoader;
import com.riserdisk.dynamicservericon.icons.IconManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dynamicservericon implements ModInitializer {

    public static final String MOD_ID = "dynamicservericon";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * Global access to the IconManager.
     * The Mixin uses this to retrieve the current icon.
     */
    public static IconManager ICON_MANAGER;

    /**
     * Global access to the icon cache.
     */
    public static IconCache ICON_CACHE;

    /**
     * Global access to the configuration.
     */
    public static ConfigManager CONFIG_MANAGER;

    @Override
    public void onInitialize() {

        LOGGER.info("Starting DynamicServerIcon...");

        CONFIG_MANAGER = new ConfigManager();

        if (!CONFIG_MANAGER.initialize()) {

            LOGGER.error("DynamicServerIcon has been disabled.");
            return;

        }

        IconLoader iconLoader = new IconLoader();

        ICON_CACHE = new IconCache(iconLoader);
        ICON_CACHE.load(CONFIG_MANAGER.getIconsDirectory());

        ModConfig config = CONFIG_MANAGER.getConfig();

        ICON_MANAGER = new IconManager(
                ICON_CACHE,
                config.getRotation().getInterval()
        );

        ICON_MANAGER.start();

        /*
         * Register server commands.
         */
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, registryAccess, environment) ->
                        DynamicServerIconCommand.register(dispatcher)
        );

        LOGGER.info("DynamicServerIcon loaded successfully.");

    }

    /**
     * Reloads the configuration and applies all runtime settings.
     */
    public static void reloadConfiguration() {

        if (CONFIG_MANAGER == null || ICON_MANAGER == null || ICON_CACHE == null) {
            return;
        }

        ModConfig config = CONFIG_MANAGER.reload();

        // Reload every icon from disk.
        ICON_CACHE.load(CONFIG_MANAGER.getIconsDirectory());

        // Apply the new interval.
        ICON_MANAGER.setRotationInterval(
                config.getRotation().getInterval()
        );

        LOGGER.info("Configuration and icons reloaded.");

    }

}