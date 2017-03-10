package de.torqdev.easysettings.core;

import static de.torqdev.easysettings.core.SettingType.UNBOUNDED;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
@SuppressWarnings("WeakerAccess")
public class UnboundedSetting<T> implements SettingContainer<T> {
    private final Setting<T> setting;

    protected UnboundedSetting(final T defaultValue, final Class<T> valueType, final String helpMessage) {
        setting = new Setting<>(UNBOUNDED, valueType, helpMessage);
        setValue(defaultValue);
    }

    @Override
    public Setting<T> getSetting() {
        return setting;
    }

    @Override
    public void setValue(final T value) {
        setting.setValue(value);
    }
}
