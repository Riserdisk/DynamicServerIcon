package com.riserdisk.dynamicservericon.icons;

import java.util.Optional;
import java.util.Random;

public class IconSelector {

    private final IconCache iconCache;
    private final Random random = new Random();

    private int lastIndex = -1;

    public IconSelector(IconCache iconCache) {
        this.iconCache = iconCache;
    }

    public Optional<ServerIcon> getNextIcon() {

        int size = iconCache.size();

        if (size == 0) {
            return Optional.empty();
        }

        if (size == 1) {
            lastIndex = 0;
            return Optional.of(iconCache.get(0));
        }

        int index;

        do {
            index = random.nextInt(size);
        } while (index == lastIndex);

        lastIndex = index;

        return Optional.of(iconCache.get(index));
    }

}