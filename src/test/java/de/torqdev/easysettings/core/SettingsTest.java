package de.torqdev.easysettings.core;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.*;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class SettingsTest {
    public static final String SETTING_NAME = "SettingName";

    private Settings testObject;

    @Before
    public void setUp() throws Exception {
        testObject = new Settings();
    }

    @Test
    public void addingSettingsWorks() throws Exception {
        Setting<String> setting = new Setting<>("DefaultValue", String.class);

        testObject.addSetting(SETTING_NAME, setting);

        assertThat(testObject.getSettings(), hasEntry(SETTING_NAME, setting));
    }

    @Test
    public void settingsCanBeFetchedByName() throws Exception {
        Setting<String> setting = new Setting<>("DefaultValue", String.class);

        testObject.addSetting(SETTING_NAME, setting);

        assertEquals(setting, testObject.get(SETTING_NAME));
    }

    @Test
    public void savingDoesntCrashIfThereIsNoHandlerRegistered() throws Exception {
        testObject.save();
    }
}