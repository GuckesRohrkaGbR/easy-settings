package de.torqdev.easysettings.core.converters;

import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class HashSetStringConverter<T> extends StringConverter<HashSet<T>> {
    private final StringConverter<T> converter;


    public HashSetStringConverter(Class<T> type) {
        Class<T> type1 = type;
        this.converter = StringConverterUtil.getConverter(type);
    }

    @Override
    public String toString(final HashSet<T> set) {
        return set.stream().map(converter::toString).collect(Collectors.joining(","));
    }

    @Override
    public HashSet<T> fromString(final String stringSet) {
        String set = sanitize(stringSet);

        HashSet<T> hashSet = new HashSet<>();

        Arrays.stream(set.split(","))
                .map(String::trim)
                .map(converter::fromString)
                .forEach(hashSet::add);

        return hashSet;
    }

    private String sanitize(final String stringSet) {
        return stringSet.replace("\\s", "").replace("[", "").replace("]", "");
    }


}
