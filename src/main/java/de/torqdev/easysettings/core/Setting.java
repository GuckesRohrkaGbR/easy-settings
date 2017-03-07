package de.torqdev.easysettings.core;

import de.torqdev.easysettings.core.converters.StringConverterUtil;
import javafx.util.StringConverter;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.util.List;

import static de.torqdev.easysettings.core.SettingType.*;
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
    private final T minValue;
    private final T maxValue;
    private final List<T> choices;


    /// TODO: Make this a builder pattern, just look at the mess you made...
    public Setting(final T value, final Class<T> valueType) {
        this(value, valueType, valueType.equals(File.class) ? FILE : UNBOUNDED, null, null,
                null, null);
    }

    public Setting(final T value, final Class<T> valueType, final String helpMessage) {
        this(value, valueType, valueType.equals(File.class) ? FILE : UNBOUNDED, null, null,
                null, helpMessage);
    }

    public Setting(final T value, final Class<T> valueType, final T minValue,
                   final T maxValue) {
        this(value, valueType, RANGE, minValue, maxValue, null, null);
    }

    public Setting(final T value, final Class<T> valueType, final T minValue, final T maxValue,
                   final String helpMessage) {
        this(value, valueType, RANGE, minValue, maxValue, null, helpMessage);
    }

    public Setting(final Class<T> valueType, final List<T> choices) {
        this(choices.get(0), valueType, choices);
    }

    public Setting(final T value, final Class<T> valueType, final List<T> choices) {
        this(value, valueType, CHOICE, null, null, choices, null);
    }

    public Setting(final T value, final Class<T> valueType, final List<T> choices,
                   final String helpMessage) {
        this(value, valueType, CHOICE, null, null, choices, helpMessage);
    }

    private Setting(final T value, final Class<T> valueType, final SettingType settingType,
                    final T minValue, final T maxValue, final List<T> choices,
                    final String helpMessage) {
        this.valueType = valueType;
        this.settingType = settingType;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.choices = choices;
        setValue(value);
        this.helpMessage = helpMessage;
    }

    public void setValue(final String value) throws EasySettingsException {
        if (value != null) {
            final StringConverter<T> converter = StringConverterUtil.getConverter(getValueType());
            setValue(converter.fromString(value));
        }
    }


    @SuppressWarnings("unchecked")
    public <R> void setValue(final R value) {
        T newVal = (T) value;
        if (value != null && valueType.isAssignableFrom(value.getClass())) {
            switch (this.settingType) {
                case RANGE:
                    newVal = capValue(newVal);
                    break;
                case CHOICE:
                    newVal = validOrDefault(newVal);
                    break;
                default:
                    break;
            }

            this.value = newVal;
        }
    }

    private T validOrDefault(final T newVal) {
        if (this.choices.contains(newVal)) {
            return newVal;
        }
        return this.value;
    }

    @SuppressWarnings("unchecked")
    private T capValue(final T value) {
        T cappedValue = value;
        if (Comparable.class.isAssignableFrom(value.getClass())) {
            final Comparable<T> original = (Comparable<T>) cappedValue;

            cappedValue = original.compareTo(maxValue) <= 0 ? cappedValue : maxValue;
            cappedValue = original.compareTo(minValue) > 0 ? cappedValue : minValue;
        }

        return cappedValue;
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

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public List<T> getChoices() {
        return choices;
    }

    @Contract(pure = true)
    public String getHelpMessage() {
        return defaultString(helpMessage);
    }
}
