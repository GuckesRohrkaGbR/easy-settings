package de.torqdev.easysettings.core;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class UnboundedSettingBuilder<T> {
    private T defaultValue;
    private Class<T> valueType;
    private String helpMessage;

    public UnboundedSettingBuilder<T> defaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public UnboundedSettingBuilder<T> forType(Class<T> clazz) {
        this.valueType = clazz;
        return this;
    }

    public UnboundedSettingBuilder<T> withHelpMessage(String helpMessage) {
        this.helpMessage = helpMessage;
        return this;
    }

    public UnboundedSetting<T> build() {
        return new UnboundedSetting<>(defaultValue, valueType, helpMessage);
    }
}
