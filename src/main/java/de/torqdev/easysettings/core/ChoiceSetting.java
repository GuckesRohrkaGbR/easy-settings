package de.torqdev.easysettings.core;

import java.util.Set;

import static de.torqdev.easysettings.core.SettingType.CHOICE;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class ChoiceSetting<T> implements SettingContainer<T> {
    private final Set<T> choices;
    private final Setting<T> setting;

    protected ChoiceSetting(T defaultValue, Class<T> valueType, Set<T> choices, String helpMessage) {
        setting = new Setting<>(CHOICE, valueType, helpMessage);
        setValue(defaultValue);
        this.choices = choices;
        choices.add(defaultValue);
    }

    @Override
    public void setValue(T value) {
        setting.setValue(value);
    }

    public Set<T> getChoices() {
        return choices;
    }

    @Override
    public Setting<T> getSetting() {
        return setting;
    }
}
