package de.torqdev.easysettings.core;

import static de.torqdev.easysettings.core.SettingType.UNBOUNDED;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class UnboundedSetting<T> extends Setting<T> {
    protected UnboundedSetting(T defaultValue, Class<T> valueType, String helpMessage) {
        super(UNBOUNDED, valueType, helpMessage);
        setValue(defaultValue);
    }
}
