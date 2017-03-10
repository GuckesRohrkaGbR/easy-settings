package de.torqdev.easysettings.core;

import org.jetbrains.annotations.Contract;

import static org.apache.commons.lang.StringUtils.defaultString;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class Setting<T> {
    private final String helpMessage;
    private T value;
    private final Class<T> valueType;
    private final SettingType settingType;

    protected Setting(final SettingType unbounded, final Class<T> valueType, final String helpMessage) {
        this.settingType = unbounded;
        this.valueType = valueType;
        this.helpMessage = helpMessage;
    }

    protected void setValue(final T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public Class<T> getValueType() {
        return valueType;
    }

    public SettingType getSettingType() {
        return settingType;
    }

    @Contract(pure = true)
    public String getHelpMessage() {
        return defaultString(helpMessage);
    }
}
