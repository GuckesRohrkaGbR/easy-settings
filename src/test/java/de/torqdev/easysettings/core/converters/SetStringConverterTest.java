package de.torqdev.easysettings.core.converters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class SetStringConverterTest extends StringConverterTest<Set<Integer>> {
    private static final SetStringConverter<Integer> intConverter =
            new SetStringConverter<>(Integer.class);

    private static final String stringRepresentation = "1,2,3,4,5";
    private static final Set<Integer> set = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

    public SetStringConverterTest() {
        super(stringRepresentation, set, intConverter);
    }
}