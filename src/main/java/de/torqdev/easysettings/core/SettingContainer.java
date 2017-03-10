package de.torqdev.easysettings.core;

import de.torqdev.easysettings.core.converters.StringConverterUtil;
import javafx.util.StringConverter;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public interface SettingContainer<T> {
    Setting<T> getSetting();

    void setValue(T value);

    default void setFromStringValue(final String stringValue) {
        final StringConverter<T> converter = StringConverterUtil.getConverter(getSetting().getValueType());
        setValue(converter.fromString(stringValue));
    }

    default T getValue() {
        return getSetting().getValue();
    }
}
