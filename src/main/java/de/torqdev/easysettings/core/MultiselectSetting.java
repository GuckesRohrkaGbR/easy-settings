package de.torqdev.easysettings.core;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class MultiselectSetting<T> implements SettingContainer<HashSet<T>> {
    private final Setting<HashSet<T>> setting;
    private final Set<T> choices;

    public MultiselectSetting(HashSet<T> defaultValue, Class<HashSet<T>> valueType, HashSet<T> choices, String helpMessage) {
        this.setting = new Setting<>(SettingType.MULTISELECT, valueType, helpMessage);
        this.setValue(defaultValue);
        this.choices = choices;
    }

    public Set<T> getChoices() {
        return choices;
    }

    @Override
    public Setting<HashSet<T>> getSetting() {
        return setting;
    }

    @Override
    public void setValue(HashSet<T> value) {
        setting.setValue(value);
    }
}
