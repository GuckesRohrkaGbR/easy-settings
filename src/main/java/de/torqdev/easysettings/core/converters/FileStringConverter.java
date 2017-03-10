package de.torqdev.easysettings.core.converters;

import javafx.util.StringConverter;

import java.io.File;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class FileStringConverter extends StringConverter<File> {
    @Override
    public String toString(final File file) {
        return (file == null) ? "" : file.toString();
    }

    @Override
    public File fromString(final String path) {
        return (path == null) ? null : new File(path);
    }
}
