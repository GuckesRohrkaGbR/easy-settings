package de.torqdev.easysettings.core;

import de.torqdev.easysettings.core.io.PropertiesHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
@SuppressWarnings("WeakerAccess")
public class SettingsImpl implements Settings {
    private final PropertiesHandler handler;

    private final Map<String, UnboundedSetting<?>> unboundedSettingMap = new HashMap<>();
    private final Map<String, ChoiceSetting<?>> choiceSettingMap = new HashMap<>();
    private final Map<String, FileSetting> fileSettingMap = new HashMap<>();
    private final Map<String, RangeSetting<?>> rangeSettingMap = new HashMap<>();
    private final Map<String, MultiselectSetting<?>> multiselectSettingMap = new HashMap<>();

    private final Map<String, SettingType> settingsTypeMap = new LinkedHashMap<>();

    public SettingsImpl(PropertiesHandler handler) {
        this.handler = handler;
        handler.setToHandle(this);
    }

    @Override
    public <T> Setting<T> getSetting(String key) {
        Setting<T> myReturn = null;

        if (settingsTypeMap.containsKey(key)) {
            switch (settingsTypeMap.get(key)) {
                case CHOICE:
                    myReturn = (Setting<T>) getChoiceSetting(key).<T>getSetting();
                    break;
                case FILE:
                    myReturn = (Setting<T>) getFileSetting(key).getSetting();
                    break;
                case MULTISELECT:
                    myReturn = (Setting<T>) getMultiselectSetting(key).<T>getSetting();
                    break;
                case RANGE:
                    myReturn = (Setting<T>) getRangeSetting(key).<T>getSetting();
                    break;
                default:
                    myReturn = (Setting<T>) getUnboundedSetting(key).<T>getSetting();
                    break;
            }
        }

        return myReturn;
    }

    @Override
    public <T> UnboundedSetting<T> getUnboundedSetting(String key) {
        return (UnboundedSetting<T>) unboundedSettingMap.get(key);
    }

    @Override
    public <T> void addUnboundedSetting(String key, UnboundedSetting<T> setting) {
        settingsTypeMap.put(key, setting.getSetting().getSettingType());
        unboundedSettingMap.put(key, setting);
    }

    @Override
    public <T> ChoiceSetting<T> getChoiceSetting(String key) {
        return (ChoiceSetting<T>) choiceSettingMap.get(key);
    }

    @Override
    public <T> void addChoiceSetting(String key, ChoiceSetting<T> setting) {
        settingsTypeMap.put(key, setting.getSetting().getSettingType());
        choiceSettingMap.put(key, setting);
    }

    @Override
    public <T extends Number> RangeSetting<T> getRangeSetting(String key) {
        return (RangeSetting<T>) rangeSettingMap.get(key);
    }

    @Override
    public <T extends Number> void addRangeSetting(String key, RangeSetting<T> setting) {
        settingsTypeMap.put(key, setting.getSetting().getSettingType());
        rangeSettingMap.put(key, setting);
    }

    @Override
    public FileSetting getFileSetting(String key) {
        return fileSettingMap.get(key);
    }

    @Override
    public void addFileSetting(String key, FileSetting setting) {
        settingsTypeMap.put(key, setting.getSetting().getSettingType());
        fileSettingMap.put(key, setting);
    }

    @Override
    public <T> MultiselectSetting<T> getMultiselectSetting(String key) {
        return (MultiselectSetting<T>) multiselectSettingMap.get(key);
    }

    @Override
    public <T> void addMultiselectSetting(String key, MultiselectSetting<T> setting) {
        settingsTypeMap.put(key, setting.getSetting().getSettingType());
        multiselectSettingMap.put(key, setting);
    }

    @Override
    public Map<String, SettingType> getSettingTypes() {
        return settingsTypeMap;
    }

    @Override
    public void save() {
        handler.saveSettingsToFile();
    }

    @Override
    public void load() {
        if (isEmpty()) {
            throw new EasySettingsException("There are no settings defined. Loading will not do anything. Define some settings first!");
        }
        handler.updateSettings();
    }

    public boolean isEmpty() {
        return settingsTypeMap.isEmpty();
    }
}
