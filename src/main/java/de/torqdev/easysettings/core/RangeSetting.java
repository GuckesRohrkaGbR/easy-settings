package de.torqdev.easysettings.core;

import static de.torqdev.easysettings.core.SettingType.RANGE;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class RangeSetting<T extends Number>implements SettingContainer<T> {
    private final T min;
    private final T max;
    private final Setting<T> setting;

    protected RangeSetting(final T defaultValue, final Class<T> valueType, final T min, final T max, final String helpMessage) {
        this.setting = new Setting<>(RANGE, valueType, helpMessage);
        this.min = min;
        this.max = max;
        setValue(defaultValue);
    }

    @Override
    public void setValue(final T defaultValue) {
        this.setting.setValue(capValue(defaultValue));
    }

    private T capValue(final T defaultValue) {
        return minimum(this.max, maximum(this.min, defaultValue));
    }

    private T maximum(final T v1, final T v2) {
        return v1.doubleValue() < v2.doubleValue() ? v2 : v1;
    }

    private T minimum(final T v1, final T v2) {
        return v1.doubleValue() > v2.doubleValue() ? v2 : v1;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }

    @Override
    public Setting<T> getSetting() {
        return setting;
    }
}
