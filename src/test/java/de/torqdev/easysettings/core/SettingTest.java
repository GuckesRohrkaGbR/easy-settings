package de.torqdev.easysettings.core;

import org.junit.Test;

import static de.torqdev.easysettings.core.SettingType.UNBOUNDED;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class SettingTest {
    @Test
    public void helpMessageIsNeverNull() throws Exception {
        // setup
        final Setting<String> setting = new Setting<>(UNBOUNDED, String.class, null);

        // execute + verify
        assertNotNull(setting.getHelpMessage());
    }
}