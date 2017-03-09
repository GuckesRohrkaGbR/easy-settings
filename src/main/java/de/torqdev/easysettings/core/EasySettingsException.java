package de.torqdev.easysettings.core;

/**
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class EasySettingsException extends RuntimeException {
    public EasySettingsException(final Throwable e) {
        super(e);
    }

    public EasySettingsException(final String s) {
        super(s);
    }
}
