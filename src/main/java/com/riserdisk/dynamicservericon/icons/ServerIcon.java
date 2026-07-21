package com.riserdisk.dynamicservericon.icons;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

public final class ServerIcon {

    private final String name;
    private final Path path;
    private final BufferedImage image;
    private final String encodedIcon;

    public ServerIcon(String name, Path path, BufferedImage image, String encodedIcon) {
        this.name = name;
        this.path = path;
        this.image = image;
        this.encodedIcon = encodedIcon;
    }

    public String getName() {
        return name;
    }

    public Path getPath() {
        return path;
    }

    public BufferedImage getImage() {
        return image;
    }

    public String getEncodedIcon() {
        return encodedIcon;
    }
}