package de.torqdev.easysettings.core.io;

import de.torqdev.easysettings.core.Settings;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public interface PropertiesHandler {
    void updateSettings();

    void saveSettingsToFile();

    void setToHandle(Settings toHandle);
}
