package com.riserdisk.dynamicservericon.icons;

import java.awt.image.BufferedImage;

public final class ImageValidator {

    private static final int REQUIRED_WIDTH = 64;
    private static final int REQUIRED_HEIGHT = 64;

    private ImageValidator() {
        // Utility class
    }

    public static boolean isValid(BufferedImage image) {

        if (image == null) {
            return false;
        }

        return image.getWidth() == REQUIRED_WIDTH
                && image.getHeight() == REQUIRED_HEIGHT;
    }

}