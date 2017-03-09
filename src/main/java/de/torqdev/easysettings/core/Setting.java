package de.torqdev.easysettings.core;

import de.torqdev.easysettings.core.converters.StringConverterUtil;
import javafx.util.StringConverter;
import org.jetbrains.annotations.Contract;

import static org.apache.commons.lang.StringUtils.defaultString;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public abstract class Setting<T> {
    private final String helpMessage;
    private T value;
    private final Class<T> valueType;
    private final SettingType settingType;

    protected Setting(SettingType unbounded, Class<T> valueType, String helpMessage) {
        this.settingType = unbounded;
        this.valueType = valueType;
        this.helpMessage = helpMessage;
    }

    public final void setFromStringValue(final String stringValue) {
        StringConverter<T> converter = StringConverterUtil.getConverter(valueType);
        setValue(converter.fromString(stringValue));
    }

    public void setValue(final T value) {
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
