package de.torqdev.easysettings.core.io;

import de.torqdev.easysettings.core.EasySettingsException;
import de.torqdev.easysettings.core.Settings;
import org.jetbrains.annotations.Contract;

import java.io.File;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public interface PropertiesHandler {
    @Contract("_ -> !null")
    static PropertiesHandler getPropertiesHandlerFor(File file) {
        return new PropertyCsvFileHandler(file);
    }

    void updateSettings(Settings toUpdate);

    void saveSettingsToFile(Settings toSave);
}
