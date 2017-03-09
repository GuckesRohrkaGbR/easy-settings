package de.torqdev.easysettings.core;

import de.torqdev.easysettings.core.converters.FileStringConverter;
import de.torqdev.easysettings.core.converters.StringConverterUtil;
import de.torqdev.easysettings.core.io.PropertiesHandler;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.*;

import java.io.File;
import java.io.FileInputStream;
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
    private final SettingBuilder builder = new SettingBuilder();

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
        settings.addSetting(LONG_NAME, builder
                .<String>unboundedSetting()
                .forType(String.class)
                .defaultValue("Long Name ShouldBeAsItWas")
                .build());

        settings.addSetting(SMALL_DOUBLE, builder
                .<Double>rangeSetting()
                .forType(Double.class)
                .defaultValue(1.5)
                .lowerBound(0.0)
                .upperBound(5.0)
                .build());

        settings.addSetting(CHOICES, builder
                .<String>choiceSetting()
                .forType(String.class)
                .defaultValue("Choice 2")
                .addChoices("Choice 1", "Choice 3")
                .build());

        settings.addSetting(FILES_ARE_SUPPORTED, builder
                .fileSetting()
                .defaultValue(new File("test.txt"))
                .build());

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
        MatcherAssert.assertThat(testSettings.get(CHOICES).getValue().toString(),
                Matchers.containsString("Choice 2"));
        MatcherAssert.assertThat(testSettings.get(FILES_ARE_SUPPORTED).getValue().toString(),
                Matchers.containsString("test.txt"));

    }
}