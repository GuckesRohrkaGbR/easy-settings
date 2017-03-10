package de.torqdev.easysettings.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class MultiselectSettingBuilder<T> {
    private final Set<T> defaultValue= new HashSet<>();
    private String helpMessage;
    private final Set<T> choices = new HashSet<>();

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
        return new MultiselectSetting<>(defaultValue, choices, helpMessage);
    }
}
