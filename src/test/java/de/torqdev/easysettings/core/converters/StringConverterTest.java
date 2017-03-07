package de.torqdev.easysettings.core.converters;

import javafx.util.StringConverter;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
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
}