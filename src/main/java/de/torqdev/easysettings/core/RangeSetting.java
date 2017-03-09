package de.torqdev.easysettings.core;

import static de.torqdev.easysettings.core.SettingType.RANGE;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class RangeSetting<T extends Comparable<T>> extends Setting<T> {
    private final T min;
    private final T max;

    protected RangeSetting(T defaultValue, Class<T> valueType, T min, T max, String helpMessage) {
        super(RANGE, valueType, helpMessage);
        setValue(defaultValue);
        this.min = min;
        this.max = max;
    }

    public T getMin() {
        return min;
    }

    public T getMax() {
        return max;
    }
}
