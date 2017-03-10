package de.torqdev.easysettings.core.converters;

import de.torqdev.easysettings.core.EasySettingsException;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class StringConverterUtilTest {
    StringConverter<Number> converter = new NumberStringConverter();

    @Test
    public void canAddNewStringConverter() throws Exception {
        StringConverterUtil.registerStringConverter(Byte.class, converter);

        StringConverter<Byte> newConverter = StringConverterUtil.getConverter(Byte.class);

        assertThat(newConverter.toString((byte) 5), equalTo("5"));
    }

    @Test(expected = EasySettingsException.class)
    public void throwsExceptionIfNoConverterWasFound() throws Exception {
        StringConverterUtil.getConverter(Object.class);
    }

    @Test
    public void coverPrivateConstructor() throws Exception {
        Constructor<?> constructor  = StringConverterUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}