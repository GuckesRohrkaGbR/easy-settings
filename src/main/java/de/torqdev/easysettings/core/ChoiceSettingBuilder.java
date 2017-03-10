package de.torqdev.easysettings.core;

import java.util.*;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class ChoiceSettingBuilder<T> {
    private T defaultValue;
    private Class<T> valueType;
    private String helpMessage;
    private final Set<T> choices = new HashSet<>();

    public ChoiceSettingBuilder<T> forType(Class<T> clazz) {
        this.valueType = clazz;
        return this;
    }

    public ChoiceSettingBuilder<T> defaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @SafeVarargs
    public final ChoiceSettingBuilder<T> addChoices(final T... choices) {
        this.choices.addAll(Arrays.asList(choices));
        return this;
    }

    public ChoiceSettingBuilder<T> withHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
        return this;
    }

    public ChoiceSetting<T> build() {
        return new ChoiceSetting<>(defaultValue, valueType, choices, helpMessage);
    }
}
