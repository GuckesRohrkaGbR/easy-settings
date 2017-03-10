package de.torqdev.easysettings.core.converters;

import javafx.util.StringConverter;

import java.util.Locale;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class LocaleStringConverter extends StringConverter<Locale> {

    @Override
    public String toString(final Locale locale) {
        return (locale == null) ? "" : locale.toLanguageTag();
    }

    @Override
    public Locale fromString(final String languageTag) {
        return (languageTag == null) ? null : Locale.forLanguageTag(languageTag);
    }
}
