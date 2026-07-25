package com.riserdisk.dynamicservericon.icons;

import net.minecraft.server.ServerMetadata;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Optional;

public final class ServerIcon {

    private final String name;
    private final Path path;
    private final BufferedImage image;
    private final Optional<ServerMetadata.Favicon> favicon;

    public ServerIcon(
            String name,
            Path path,
            BufferedImage image,
            Optional<ServerMetadata.Favicon> favicon) {

        this.name = name;
        this.path = path;
        this.image = image;
        this.favicon = favicon;
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

    public Optional<ServerMetadata.Favicon> getFavicon() {
        return favicon;
    }

}