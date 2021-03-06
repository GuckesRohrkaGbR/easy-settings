package de.torqdev.easysettings.core.io;

import de.torqdev.easysettings.core.*;
import de.torqdev.easysettings.core.converters.StringConverterUtil;

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
public class PropertiesFileHandler implements PropertiesHandler {
    private final File propertiesFile;
    private Settings toHandle;
    private final Properties properties = new Properties();

    public PropertiesFileHandler(final File propertiesFile) {
        this.propertiesFile = propertiesFile;
    }

    @Override
    public void updateSettings() {
        try (FileInputStream fis = new FileInputStream(propertiesFile)) {
            properties.load(fis);
            toHandle.getSettingTypes().forEach(this::updateSetting);
        } catch (final IOException e) {
            throw new EasySettingsException(e);
        }
    }

    private void updateSetting(final String key, final SettingType type) {
        switch (type) {
            case FILE:
                updateFileSetting(key);
                break;
            case CHOICE:
                updateChoiceSetting(key);
                break;
            case RANGE:
                updateRangeSetting(key);
                break;
            case MULTISELECT:
                updateMultiselectSetting(key);
                break;
            default:
                updateUnboundedSetting(key);
                break;
        }
    }

    private void updateFileSetting(final String key) {
        final String value = getValueForKey(key);
        toHandle.getFileSetting(key).setFromStringValue(value);
    }

    private void updateUnboundedSetting(final String key) {
        final String value = getValueForKey(key);
        toHandle.getUnboundedSetting(key).setFromStringValue(value);
    }

    private void updateChoiceSetting(final String key) {
        final String value = getValueForKey(key);
        toHandle.getChoiceSetting(key).setFromStringValue(value);
    }

    private void updateRangeSetting(final String key) {
        final String value = getValueForKey(key);
        toHandle.getRangeSetting(key).setFromStringValue(value);
    }

    private void updateMultiselectSetting(final String key) {
        final String value = getValueForKey(key);
        toHandle.getMultiselectSetting(key).setFromStringValue(value);
    }

    private String getValueForKey(final String key) {
        final String keyName = propertizeKey(key);
        return properties.getProperty(keyName);
    }

    private void setValueForKey(final String key, final String value) {
        final String keyName = propertizeKey(key);
        properties.put(keyName, value);
    }

    @Override
    public void saveSettingsToFile() {
        toHandle.getSettingTypes().keySet().forEach(this::saveSetting);

        try (FileOutputStream fos = new FileOutputStream(propertiesFile)) {
            properties.store(fos, null);
        } catch (final IOException e) {
            throw new EasySettingsException(e);
        }
    }

    private void saveSetting(final String key) {
        final String value = valueToString(toHandle.getSetting(key));
        setValueForKey(key, value);
    }

    private String valueToString(final Setting<Object> setting) {
        return StringConverterUtil.getConverter(setting.getValueType()).toString(setting.getValue());
    }

    private String propertizeKey(final String key) {
        return key.toLowerCase(Locale.ENGLISH).replaceAll("\\s+", ".");
    }

    @Override
    public void setToHandle(final Settings toHandle) {
        this.toHandle = toHandle;
    }
}
