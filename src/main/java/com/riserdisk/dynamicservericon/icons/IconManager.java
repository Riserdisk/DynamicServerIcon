package com.riserdisk.dynamicservericon.icons;

import com.riserdisk.dynamicservericon.Dynamicservericon;

import net.minecraft.server.ServerMetadata;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IconManager {

    // Durante el desarrollo usaremos 10 segundos.
    // Más adelante esto saldrá del config.json.
    private static final int ROTATION_INTERVAL_SECONDS = 10;

    private final IconCache iconCache;
    private final IconSelector iconSelector;

    private final ScheduledExecutorService scheduler =
        Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r);
            thread.setName("DynamicServerIcon Scheduler");
            thread.setDaemon(true);
            return thread;
        });

    private volatile ServerIcon currentIcon;

    public IconManager(IconCache iconCache) {

        this.iconCache = iconCache;
        this.iconSelector = new IconSelector(iconCache);

    }

    public void start() {

        if (iconCache.isEmpty()) {

            Dynamicservericon.LOGGER.warn("No icons available.");

            return;

        }

        rotateIcon();

        scheduler.scheduleAtFixedRate(
                this::rotateIcon,
                ROTATION_INTERVAL_SECONDS,
                ROTATION_INTERVAL_SECONDS,
                TimeUnit.SECONDS
        );

        Dynamicservericon.LOGGER.info(
                "Icon rotation started ({} seconds).",
                ROTATION_INTERVAL_SECONDS
        );

    }

    private void rotateIcon() {

        Optional<ServerIcon> icon = iconSelector.getNextIcon();

        if (icon.isEmpty()) {
            return;
        }

        currentIcon = icon.get();

        Dynamicservericon.LOGGER.info(
                "Current icon -> {}",
                currentIcon.getName()
        );

    }

    public ServerIcon getCurrentIcon() {

        return currentIcon;

    }

}