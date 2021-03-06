package de.torqdev.easysettings.core.converters;

import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class SetStringConverter<T> extends StringConverter<Set<T>> {
    private final StringConverter<T> converter;

    public SetStringConverter(final Class<T> type) {
        this.converter = StringConverterUtil.getConverter(type);
    }

    @Override
    public String toString(final Set<T> set) {
        return (set == null) ? "" : set.stream().map(converter::toString).collect(Collectors.joining(","));
    }

    @Override
    public Set<T> fromString(final String stringSet) {
        final Set<T> myReturn = new HashSet<>();
        if(stringSet != null) {
            final String set = sanitize(stringSet);
            Arrays.stream(set.split(","))
                    .map(String::trim)
                    .map(converter::fromString)
                    .forEach(myReturn::add);
        }

        return myReturn;
    }

    private String sanitize(final String stringSet) {
        return stringSet.replace("\\s", "").replace("[", "").replace("]", "");
    }
}
