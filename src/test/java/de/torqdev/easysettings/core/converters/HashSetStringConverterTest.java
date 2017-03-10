package de.torqdev.easysettings.core.converters;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class HashSetStringConverterTest {
    private HashSetStringConverter<String> stringConverter = new HashSetStringConverter<>(String.class);
    private HashSetStringConverter<Integer> intConverter = new HashSetStringConverter<>(Integer.class);

    @Test
    public void translatesHashSetIntoStringAndBack() throws Exception {
        // setup
        HashSet<String> set = new HashSet<>(Arrays.asList("Test 1", "Test 2", "Test 3"));

        // execute
        String string = stringConverter.toString(set);
        HashSet<String> stringSet = stringConverter.fromString(string);

        // verify
        assertThat(stringSet, hasItems("Test 1", "Test 2", "Test 3"));
        assertThat(stringSet, equalTo(set));
    }

    @Test
    public void translatesStringToHashSetAndBack() throws Exception {
        // setup
        String string = "1,2,3,4,5";

        // execute
        HashSet<Integer> set = intConverter.fromString(string);
        String setString = intConverter.toString(set);

        // verify
        assertThat(set, contains(1,2,3,4,5));
        assertThat(setString, equalTo(string));
    }
}