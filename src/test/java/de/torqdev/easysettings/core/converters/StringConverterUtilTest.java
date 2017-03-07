package de.torqdev.easysettings.core.converters;

import de.torqdev.easysettings.core.EasySettingsException;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

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
}