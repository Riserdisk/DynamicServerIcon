package com.riserdisk.dynamicservericon;

import com.riserdisk.dynamicservericon.config.ConfigManager;
import com.riserdisk.dynamicservericon.icons.IconCache;
import com.riserdisk.dynamicservericon.icons.IconLoader;
import com.riserdisk.dynamicservericon.icons.IconManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dynamicservericon implements ModInitializer {

    public static final String MOD_ID = "dynamicservericon";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * Global access to the IconManager.
     * The Mixin will use this to retrieve the current icon.
     */
    public static IconManager ICON_MANAGER;

    @Override
    public void onInitialize() {

        LOGGER.info("Starting DynamicServerIcon...");

        ConfigManager configManager = new ConfigManager();

        if (!configManager.initialize()) {
            LOGGER.error("DynamicServerIcon has been disabled.");
            return;
        }

        IconLoader iconLoader = new IconLoader();
        IconCache iconCache = new IconCache(iconLoader);

        iconCache.load(configManager.getIconsDirectory());

        ICON_MANAGER = new IconManager(iconCache);
        ICON_MANAGER.start();

        LOGGER.info("DynamicServerIcon loaded successfully.");

    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

}