package de.torqdev.easysettings.core.converters;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class HashSetStringConverterTest extends StringConverterTest<HashSet<Integer>> {
    private static final HashSetStringConverter<Integer> intConverter =
            new HashSetStringConverter<>(Integer.class);

    private static final String stringRepresentation = "1,2,3,4,5";
    private static final HashSet<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

    public HashSetStringConverterTest() {
        super(stringRepresentation, set, intConverter);
    }
}