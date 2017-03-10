package de.torqdev.easysettings.core.converters;

import de.torqdev.easysettings.core.EasySettingsException;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.BigIntegerStringConverter;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.ByteStringConverter;
import javafx.util.converter.CharacterStringConverter;
import javafx.util.converter.DateTimeStringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.LongStringConverter;
import javafx.util.converter.NumberStringConverter;
import javafx.util.converter.ShortStringConverter;
import org.jetbrains.annotations.Contract;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public final class StringConverterUtil {
    private static final Map<Class<?>, StringConverter<?>> converters = fillMapWithDefaults();

    private StringConverterUtil() {
        // empty utility class constructor
    }

    @Contract(pure = true)
    private static Map<Class<?>, StringConverter<?>> fillMapWithDefaults() {
        final Map<Class<?>, StringConverter<?>> myReturn = new ConcurrentHashMap<>();

        myReturn.put(BigDecimal.class, new BigDecimalStringConverter());
        myReturn.put(BigInteger.class, new BigIntegerStringConverter());
        myReturn.put(Boolean.class, new BooleanStringConverter());
        myReturn.put(Byte.class, new ByteStringConverter());
        myReturn.put(Character.class, new CharacterStringConverter());
        myReturn.put(Color.class, new ColorStringConverter());
        myReturn.put(Date.class, new DateTimeStringConverter());
        myReturn.put(Double.class, new DoubleStringConverter());
        myReturn.put(File.class, new FileStringConverter());
        myReturn.put(Float.class, new FloatStringConverter());
        myReturn.put(Integer.class, new IntegerStringConverter());
        myReturn.put(LocalDate.class, new LocalDateStringConverter());
        myReturn.put(Locale.class, new LocaleStringConverter());
        myReturn.put(LocalTime.class, new LocalTimeStringConverter());
        myReturn.put(Long.class, new LongStringConverter());
        myReturn.put(Number.class, new NumberStringConverter());
        myReturn.put(Short.class, new ShortStringConverter());
        myReturn.put(String.class, new DefaultStringConverter());

        return myReturn;
    }

    public static void registerStringConverter(final Class<?> clazz, final StringConverter<?> converter) {
        converters.put(clazz, converter);
    }

    @SuppressWarnings("unchecked")
    public static <T> StringConverter<T> getConverter(final Class<T> valueType) {
        final StringConverter<T> myReturn = (StringConverter<T>) converters.get(valueType);
        if(myReturn == null) {
            throw new EasySettingsException("No String converter for data type " + valueType.getCanonicalName() + " found!");
        }
        return myReturn;
    }
}
