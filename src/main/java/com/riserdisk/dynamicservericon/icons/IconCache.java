package com.riserdisk.dynamicservericon.icons;

import com.riserdisk.dynamicservericon.Dynamicservericon;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class IconCache {

    private final List<ServerIcon> icons = new ArrayList<>();
    private final IconLoader loader;

    public IconCache(IconLoader loader) {
        this.loader = loader;
    }

    public void load(Path iconsDirectory) {

        icons.clear();

        if (!Files.exists(iconsDirectory)) {
            Dynamicservericon.LOGGER.warn("Icons directory does not exist.");
            return;
        }

        try (Stream<Path> paths = Files.list(iconsDirectory)) {

            paths.filter(Files::isRegularFile)
                    .forEach(path -> loader.load(path).ifPresent(icons::add));

        } catch (IOException e) {

            Dynamicservericon.LOGGER.error("Failed to scan icons directory.", e);

        }

        Dynamicservericon.LOGGER.info("Loaded {} icon(s).", icons.size());

    }

    public List<ServerIcon> getIcons() {
        return List.copyOf(icons);
    }

    public ServerIcon get(int index) {
        return icons.get(index);
    }

    public boolean isEmpty() {
        return icons.isEmpty();
    }

    public int size() {
        return icons.size();
    }

}