package de.torqdev.easysettings.core;

import de.torqdev.easysettings.core.io.PropertiesHandler;
import de.torqdev.easysettings.core.io.PropertiesFileHandler;

import java.io.File;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
@SuppressWarnings("ConstantConditions")
public class SettingsImplTest extends SettingsTest {
    private static final File CSV_FILE = new File(
            SettingsIT.class
                    .getClassLoader()
                    .getResource("properties/settings-impl-test.properties")
                    .getFile()
    );
    private static final PropertiesHandler HANDLER = new PropertiesFileHandler(CSV_FILE);

    @Override
    protected Settings getNewTestObject() {
        return new SettingsImpl(HANDLER);
    }
}
