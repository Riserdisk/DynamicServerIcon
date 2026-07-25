package com.riserdisk.dynamicservericon.icons;

import com.riserdisk.dynamicservericon.Dynamicservericon;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IconManager {

    private final IconCache iconCache;
    private final IconSelector iconSelector;

    private ScheduledExecutorService scheduler;

    /**
     * Rotation interval in seconds.
     * Loaded from config.json.
     */
    private volatile int rotationIntervalSeconds;

    private volatile ServerIcon currentIcon;

    public IconManager(
            IconCache iconCache,
            int rotationIntervalSeconds
    ) {

        this.iconCache = iconCache;
        this.iconSelector = new IconSelector(iconCache);
        this.rotationIntervalSeconds = rotationIntervalSeconds;

    }

    public void start() {

        if (iconCache.isEmpty()) {

            Dynamicservericon.LOGGER.warn("No icons available.");
            return;

        }

        rotateIcon();

        restartScheduler();

        Dynamicservericon.LOGGER.info(
                "Icon rotation started ({} seconds).",
                rotationIntervalSeconds
        );

    }

    /**
     * Changes the rotation interval.
     * If the scheduler is already running,
     * it is automatically restarted.
     */
    public void setRotationInterval(int seconds) {

        if (seconds <= 0) {
            throw new IllegalArgumentException(
                    "Rotation interval must be greater than zero."
            );
        }

        if (rotationIntervalSeconds == seconds) {
            return;
        }

        rotationIntervalSeconds = seconds;

        Dynamicservericon.LOGGER.info(
                "Rotation interval changed to {} seconds.",
                rotationIntervalSeconds
        );

        if (scheduler != null) {
            restartScheduler();
        }

    }

    /**
     * Stops the current scheduler and creates a new one.
     */
    private void restartScheduler() {

        shutdownScheduler();

        scheduler = createScheduler();

        scheduler.scheduleAtFixedRate(
                this::rotateIcon,
                rotationIntervalSeconds,
                rotationIntervalSeconds,
                TimeUnit.SECONDS
        );

    }

    private ScheduledExecutorService createScheduler() {

        return Executors.newSingleThreadScheduledExecutor(r -> {

            Thread thread = new Thread(r);

            thread.setName("DynamicServerIcon Scheduler");
            thread.setDaemon(true);

            return thread;

        });

    }

    private void shutdownScheduler() {

        if (scheduler != null) {

            scheduler.shutdownNow();

            scheduler = null;

        }

    }

    public void shutdown() {

        shutdownScheduler();

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

    public int getRotationIntervalSeconds() {

        return rotationIntervalSeconds;

    }

}