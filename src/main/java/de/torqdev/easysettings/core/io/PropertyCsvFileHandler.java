package de.torqdev.easysettings.core.io;

import de.torqdev.easysettings.core.EasySettingsException;
import de.torqdev.easysettings.core.Settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
class PropertyCsvFileHandler implements PropertiesHandler {
    private final File propertiesFile;

    public PropertyCsvFileHandler(final File propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    @Override
    public void updateSettings(final Settings toUpdate) throws EasySettingsException {
        final Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            properties.load(fis);

            toUpdate.getSettings().forEach((key, setting) -> {
                final String keyName = propertizeKey(key);
                try {
                    setting.setValue(properties.getProperty(keyName));
                } catch (EasySettingsException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new EasySettingsException(e);
        }
    }

    @Override
    public void saveSettingsToFile(final Settings toSave) throws EasySettingsException {
        final Properties properties = new Properties();

        toSave.getSettings().forEach((key, setting) -> {
            final String keyName = propertizeKey(key);
            final String propertyValue = setting.getValue().toString();
            properties.setProperty(keyName, propertyValue);
        });

        try (FileOutputStream fos = new FileOutputStream(propertiesFile)) {
            properties.store(fos, null);
        } catch (IOException e) {
            throw new EasySettingsException(e);
        }
    }

    private String propertizeKey(final String key) {
        return key.toLowerCase(Locale.ENGLISH).replaceAll("\\s+", ".");
    }
}
