package com.riserdisk.dynamicservericon.icons;

import com.riserdisk.dynamicservericon.Dynamicservericon;

import net.minecraft.server.ServerMetadata;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

public class IconLoader {

    public Optional<ServerIcon> load(Path path) {

        BufferedImage image;

        try {

            image = ImageIO.read(path.toFile());

        } catch (IOException e) {

            Dynamicservericon.LOGGER.warn("Failed to read icon: {}", path.getFileName());

            return Optional.empty();

        }

        if (!ImageValidator.isValid(image)) {

            Dynamicservericon.LOGGER.warn(
                    "Invalid icon '{}'. Expected a 64x64 PNG.",
                    path.getFileName()
            );

            return Optional.empty();

        }

        Optional<ServerMetadata.Favicon> favicon;

        try {

            byte[] pngBytes = Files.readAllBytes(path);

            favicon = Optional.of(new ServerMetadata.Favicon(pngBytes));

        } catch (IOException e) {

            Dynamicservericon.LOGGER.warn(
                    "Failed to load favicon bytes: {}",
                    path.getFileName()
            );

            return Optional.empty();

        }

        ServerIcon icon = new ServerIcon(
                path.getFileName().toString(),
                path,
                image,
                favicon
        );

        Dynamicservericon.LOGGER.info("Loaded icon '{}'.", icon.getName());

        return Optional.of(icon);

    }

}