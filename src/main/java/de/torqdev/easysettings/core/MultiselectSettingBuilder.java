package de.torqdev.easysettings.core;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class MultiselectSettingBuilder<T> {
    private final HashSet<T> defaultValue= new HashSet<>();
    private Class<HashSet<T>> valueType;
    private String helpMessage;
    private final HashSet<T> choices = new HashSet<>();

    public MultiselectSettingBuilder<T> forType(Class<HashSet<T>> clazz) {
        this.valueType = clazz;
        return this;
    }

    @SafeVarargs
    public final MultiselectSettingBuilder<T> defaultValue(T... defaultValue) {
        this.defaultValue.addAll(Arrays.asList(defaultValue));
        return this;
    }

    @SafeVarargs
    public final MultiselectSettingBuilder<T> addChoices(final T... choices) {
        this.choices.addAll(Arrays.asList(choices));
        return this;
    }

    public MultiselectSettingBuilder<T> withHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
        return this;
    }

    public MultiselectSetting<T> build() {
        if(!choices.containsAll(defaultValue)) {
            choices.addAll(defaultValue);
        }
        return new MultiselectSetting<>(defaultValue, valueType, choices, helpMessage);
    }
}
