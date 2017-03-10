package de.torqdev.easysettings.core;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class MultiselectSetting<T> implements SettingContainer<Set<T>> {
    private final Setting<Set<T>> setting;
    private final Set<T> choices;

    public MultiselectSetting(Set<T> defaultValue, Set<T> choices, String helpMessage) {
        this.setting = new Setting<Set<T>>(SettingType.MULTISELECT, getSetType(), helpMessage);
        this.setValue(defaultValue);
        this.choices = choices;
        this.choices.addAll(defaultValue);
    }

    @SuppressWarnings("unchecked")
    private Class<Set<T>> getSetType() {
        Set<T> myReturn = new HashSet<T>();
        return (Class<Set<T>>) myReturn.getClass();
    }

    public Set<T> getChoices() {
        return choices;
    }

    @Override
    public Setting<Set<T>> getSetting() {
        return setting;
    }

    @Override
    public void setValue(Set<T> value) {
        setting.setValue(value);
    }
}
