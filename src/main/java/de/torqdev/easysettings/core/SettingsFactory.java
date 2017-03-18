package de.torqdev.easysettings.core;

import de.torqdev.easysettings.core.io.PropertiesFileHandler;
import de.torqdev.easysettings.core.io.PropertiesHandler;

import java.io.File;

/**
 * This class allows you to get
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public final class SettingsFactory {
    private SettingsFactory() {
        // private utility class constructor
    }

    public static Settings getSettings(final File file) {
        final PropertiesHandler handler = new PropertiesFileHandler(file);
        return new SettingsImpl(handler);
    }
}
