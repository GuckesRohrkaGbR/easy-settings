package de.torqdev.easysettings.core;

import java.util.Set;

import static de.torqdev.easysettings.core.SettingType.CHOICE;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class ChoiceSetting<T> extends Setting<T> {
    private final Set<T> choices;

    protected ChoiceSetting(T defaultValue, Class<T> valueType, Set<T> choices, String helpMessage) {
        super(CHOICE, valueType, helpMessage);
        setValue(defaultValue);
        this.choices = choices;
    }

    public Set<T> getChoices() {
        return choices;
    }
}
