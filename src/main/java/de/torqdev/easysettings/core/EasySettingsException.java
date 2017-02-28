package de.torqdev.easysettings.core;

import java.io.IOException;

/**
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class EasySettingsException extends Exception {
    public EasySettingsException(final Throwable e) {
        super(e);
    }

    public EasySettingsException(final String s) {
        super(s);
    }
}
