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

    public final void setFromStringValue(final String stringValue) throws EasySettingsException {
        StringConverter<T> converter = StringConverterUtil.getConverter(valueType);
        setValue(converter.fromString(stringValue));
    }

    public void setValue(final T value) {
        this.value = value;
    }

//        if (value != null && valueType.isAssignableFrom(value.getClass())) {
//            switch (this.settingType) {
//                case RANGE:
//                    newVal = capValue(newVal);
//                    break;
//                case CHOICE:
//                    newVal = validOrDefault(newVal);
//                    break;
//                default:
//                    break;
//            }
//
//            this.value = newVal;
//        }
//    }
//
//    private T validOrDefault(final T newVal) {
//        if (this.choices.contains(newVal)) {
//            return newVal;
//        }
//        return this.value;
//    }

//    @SuppressWarnings("unchecked")
//    private T capValue(final T value) {
//        T cappedValue = value;
//        if (Comparable.class.isAssignableFrom(value.getClass())) {
//            final Comparable<T> original = (Comparable<T>) cappedValue;
//
//            cappedValue = original.compareTo(maxValue) <= 0 ? cappedValue : maxValue;
//            cappedValue = original.compareTo(minValue) > 0 ? cappedValue : minValue;
//        }
//
//        return cappedValue;
//    }

    public T getValue() {
        return value;
    }

    public Class<T> getValueType() {
        return valueType;
    }

    public SettingType getSettingType() {
        return settingType;
    }
//
//    public T getMinValue() {
//        return minValue;
//    }
//
//    public T getMaxValue() {
//        return maxValue;
//    }
//
//    public List<T> getChoices() {
//        return choices;
//    }

    @Contract(pure = true)
    public String getHelpMessage() {
        return defaultString(helpMessage);
    }
}
