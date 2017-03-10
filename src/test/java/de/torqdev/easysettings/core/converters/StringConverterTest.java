package de.torqdev.easysettings.core.converters;

import javafx.util.StringConverter;
import org.hamcrest.Matchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public abstract class StringConverterTest<T> {
    private final String stringRepresentation;
    private final T object;

    private final StringConverter<T> testObject;

    StringConverterTest(String stringRepresentation, T object, StringConverter<T> testObject) {
        this.stringRepresentation = stringRepresentation;
        this.object = object;
        this.testObject = testObject;
    }

    @Test
    public void canTranslateToString() throws Exception {
        assertThat(testObject.toString(object), equalTo(stringRepresentation));
    }

    @Test
    public void canTranslateFromString() throws Exception {
        assertThat(testObject.fromString(stringRepresentation), equalTo(object));
    }

    @Test
    public void translatesToStringAndBack() throws Exception {
        // execute
        String string = testObject.toString(object);
        T stringObject = testObject.fromString(string);

        // verify
        assertThat(stringObject, Matchers.equalTo(object));
    }

    @Test
    public void translatesFromStringAndBack() throws Exception {
        // execute
        T stringObject = testObject.fromString(stringRepresentation);
        String string = testObject.toString(object);

        // verify
        assertThat(string, Matchers.equalTo(stringRepresentation));
    }

    @Test
    public void nullToStringIsNeverNull() throws Exception {
        assertThat(testObject.toString(null), notNullValue());
    }

    @Test
    public void fromStringWithNullReturnsNull() throws Exception {
        assertThat(testObject.fromString(null), nullValue());
    }
}