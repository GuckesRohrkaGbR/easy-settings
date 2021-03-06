package de.torqdev.easysettings.core.io;

import java.io.File;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public final class PropertiesHandlerFactory {
    private PropertiesHandlerFactory() {
        // Private Utility Class constructor
    }

    public static PropertiesHandler getHandlerFor(final File file) {
        PropertiesHandler handler = null;

        if(!file.getAbsolutePath().endsWith(".xml")) {
            handler = new PropertiesFileHandler(file);
        }

        return handler;
    }
}
