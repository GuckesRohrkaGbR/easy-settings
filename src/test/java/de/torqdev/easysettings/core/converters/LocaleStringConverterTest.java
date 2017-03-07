package de.torqdev.easysettings.core.converters;

import java.util.Locale;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class LocaleStringConverterTest extends StringConverterTest<Locale> {
    private static final String languageTag = "de-DE";
    private static final Locale locale = Locale.GERMANY;

    public LocaleStringConverterTest() {
        super(languageTag, locale, new LocaleStringConverter());
    }
}
