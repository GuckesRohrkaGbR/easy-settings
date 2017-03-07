package de.torqdev.easysettings.core;

import de.torqdev.easysettings.core.converters.FileStringConverter;
import de.torqdev.easysettings.core.converters.StringConverterUtil;
import de.torqdev.easysettings.core.io.PropertiesHandler;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class PropertyCsvFileHandlerTest {
    private static final String TEST_FILENAME = System.getProperty(
            "java.io.tmpdir") + File.separator + "property-file-handler-test-file.properties";
    private static final String LONG_NAME = "Long Name";
    private static final String SMALL_DOUBLE = "small double";
    private static final String CHOICES = "Choices Choices Choices";
    private static final String FILES_ARE_SUPPORTED = "Files in UTF-8: ÆÆÆÆÆ";

    private File testFile;
    private PropertiesHandler testObject;
    private Settings testSettings;

    @BeforeClass
    public static void setUpRegisterFileStringConverter() {
        StringConverterUtil.registerStringConverter(File.class, new FileStringConverter());
    }

    @Before
    public void setUp() throws Exception {
        testFile = new File(TEST_FILENAME);
        testObject = PropertiesHandler.getPropertiesHandlerFor(testFile);
        testSettings = generateTestSettings();
    }

    private Settings generateTestSettings() {
        Settings settings = new Settings();
        settings.addSetting(LONG_NAME, new Setting<>("Long Name ShouldBeAsItWas", String.class));
        settings.addSetting(SMALL_DOUBLE, new Setting<>(1.5, Double.class, 0.0, 5.0));
        settings.addSetting(CHOICES, new Setting<>("Choice 2", String.class,
                                                   Arrays.asList("Choice 1", "Choice 2",
                                                                 "Choice 3")));
        settings.addSetting(FILES_ARE_SUPPORTED, new Setting<>(new File("test.txt"), File.class));
        return settings;
    }

    @After
    public void tearDown() throws Exception {
        //noinspection ResultOfMethodCallIgnored
        testFile.delete();
    }

    @Test
    public void createsPropertiesFile() throws Exception {
        testObject.saveSettingsToFile(testSettings);
        Assert.assertTrue(testFile.exists());
        Assert.assertTrue(testFile.isFile());
    }

    @Test
    public void storesSettingsObjectInFile() throws Exception {
        testObject.saveSettingsToFile(testSettings);

        final Properties createdFile = new Properties();
        createdFile.load(new FileInputStream(testFile));

        testSettings.getSettings().forEach((key, setting) -> MatcherAssert.assertThat(
                "Property key " + propertizeKey(key) + " doesn't contain original value!",
                createdFile.getProperty(propertizeKey(key)),
                Matchers.containsString(setting.getValue().toString())));
    }

    private String propertizeKey(String key) {
        return key.toLowerCase().replaceAll("\\s+", ".");
    }

    @Test
    public void loadsPropertiesFromFileAndUpdatesExistingSettings() throws Exception {
        testObject.saveSettingsToFile(testSettings);
        testSettings.get(LONG_NAME).setValue("Short Name");
        testSettings.get(SMALL_DOUBLE).setValue(0.5);
        testSettings.get(CHOICES).setValue("Choice 3");
        testSettings.get(FILES_ARE_SUPPORTED).setValue("Test");

        testObject.updateSettings(testSettings);

        MatcherAssert.assertThat(testSettings.get(LONG_NAME).getValue().toString(),
                   Matchers.containsString("Long Name ShouldBeAsItWas"));
        MatcherAssert.assertThat(testSettings.get(SMALL_DOUBLE).getValue().toString(),
                   Matchers.containsString(Double.valueOf(1.5).toString()));
        MatcherAssert.assertThat(testSettings.get(CHOICES).getValue().toString(), Matchers.containsString("Choice 2"));
        MatcherAssert.assertThat(testSettings.get(FILES_ARE_SUPPORTED).getValue().toString(),
                   Matchers.containsString("test.txt"));

    }
}