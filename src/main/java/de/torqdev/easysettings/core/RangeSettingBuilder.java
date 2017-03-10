package de.torqdev.easysettings.core;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class RangeSettingBuilder<T extends Number> {
    private T defaultValue;
    private Class<T> valueType;
    private T min;
    private T max;
    private String helpMessage;

    public RangeSettingBuilder<T> forType(final Class<T> clazz) {
        this.valueType = clazz;
        return this;
    }

    public RangeSettingBuilder<T> defaultValue(final T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }


    public RangeSettingBuilder<T> lowerBound(final T min) {
        this.min = min;
        return this;
    }

    public RangeSettingBuilder<T> upperBound(final T max) {
        this.max = max;
        return this;
    }

    public RangeSettingBuilder<T> withHelpMessage(final String helpMessage) {
        this.helpMessage = helpMessage;
        return this;
    }

    public RangeSetting<T> build() {
        return new RangeSetting<>(defaultValue, valueType, min, max, helpMessage);
    }
}
