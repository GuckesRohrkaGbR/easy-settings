package de.torqdev.easysettings.core.converters;

import org.junit.Test;

import java.io.File;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class FileStringConverterTest extends StringConverterTest<File> {
    private static final String path = System.getProperty("java.io.tmpdir") + "tmpfile";
    private static final File file = new File(path);

    public FileStringConverterTest() {
        super(path, file, new FileStringConverter());
    }
}
