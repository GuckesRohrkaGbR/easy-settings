package de.torqdev.easysettings.core.converters;

import javafx.util.StringConverter;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public abstract class StringConverterTest<T> {
    private final String stringRepresentation;
    private final T object;

    private final StringConverter<T> testObject;

    StringConverterTest(final String stringRepresentation, final T object, final StringConverter<T> testObject) {
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
        final String string = testObject.toString(object);
        final T stringObject = testObject.fromString(string);

        // verify
        assertThat(stringObject, Matchers.equalTo(object));
    }

    @Test
    public void translatesFromStringAndBack() throws Exception {
        // execute
        final T stringObject = testObject.fromString(stringRepresentation);
        final String string = testObject.toString(object);

        // verify
        assertThat(string, Matchers.equalTo(stringRepresentation));
    }

    @Test
    public void nullToStringIsNeverNull() throws Exception {
        assertThat(testObject.toString(null), notNullValue());
    }

    @Test
    public void fromStringWithNullReturnsNullOrEmptyCollection() throws Exception {
        final T object = testObject.fromString(null);
        if(object != null) {
            assertTrue(Collection.class.isAssignableFrom(object.getClass()));
            assertThat(((Collection) object).size(), equalTo(0));
        }
    }
}