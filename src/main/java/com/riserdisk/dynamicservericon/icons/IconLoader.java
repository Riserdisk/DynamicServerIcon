package com.riserdisk.dynamicservericon.icons;

import com.riserdisk.dynamicservericon.Dynamicservericon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
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

        // Temporal.
        // Más adelante reemplazaremos esto por la conversión real.
        String encoded = Base64.getEncoder().encodeToString(new byte[0]);

        ServerIcon icon = new ServerIcon(
                path.getFileName().toString(),
                path,
                image,
                encoded
        );

        Dynamicservericon.LOGGER.info("Loaded icon '{}'.", icon.getName());

        return Optional.of(icon);

    }

}