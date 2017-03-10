package de.torqdev.easysettings.core.io;

import de.torqdev.easysettings.core.*;
import de.torqdev.easysettings.core.converters.HashSetStringConverter;
import de.torqdev.easysettings.core.converters.StringConverterUtil;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class PropertiesFileHandlerTest {
    private static final File PROPERTIES_FILE = new File(
            PropertiesFileHandlerTest.class
                    .getClassLoader()
                    .getResource("properties/integration-test.properties")
                    .getFile()
    );
    private static final String UNBOUNDED_SETTING = "Unbounded Setting";
    private static final String HELP_MESSAGE = "Help Message";
    private static final String DEFAULT_VALUE = "Default Value";
    private static final String CHOICE_SETTING = "Choice Setting";
    private static final String RANGE_SETTING = "Range Setting";
    private static final String MULTISELECT_SETTING = "Multiselect Setting";
    private static final String FILE_SETTING = "File Setting";
    private static final File DEFAULT_FILE = new File(System.getProperty("java.io.tmpdir") + "file");

    private Settings settings;
    private PropertiesHandler handler;

    @BeforeClass
    public static void registerHashMapStringConverter() {
        StringConverterUtil.registerStringConverter(new HashSet<Locale>().getClass(), new HashSetStringConverter<>(Locale.class));
    }

    @Before
    public void setUp() throws Exception {
        handler = new PropertiesFileHandler(PROPERTIES_FILE);
        settings = new SettingsImpl(handler);
        fillSettings(settings);

    }

    @Test
    public void canStoreAndLoadUnboundedSettingFromFile() throws Exception {
        // execute
        settings.save();
        settings.getUnboundedSetting(UNBOUNDED_SETTING).setValue("Not the default value");

        // verify
        assertThat(settings.getUnboundedSetting(UNBOUNDED_SETTING).getValue(), equalTo("Not the default value"));
        settings.load();
        assertThat(settings.getUnboundedSetting(UNBOUNDED_SETTING).getValue(), equalTo(DEFAULT_VALUE));
    }

    @Test
    public void canStoreAndLoadRangeSettingFromFile() throws Exception {
        // execute
        settings.save();
        settings.getRangeSetting(RANGE_SETTING).setValue(0.0);

        // verify
        assertThat(settings.getRangeSetting(RANGE_SETTING).getValue(), equalTo(0.0));
        settings.load();
        assertThat(settings.getRangeSetting(RANGE_SETTING).getValue(), equalTo(1.0));
    }

    @Test
    public void canStoreAndLoadChoiceSettingFromFile() throws Exception {
        // execute
        settings.save();
        settings.getChoiceSetting(CHOICE_SETTING).setValue(Color.BLUE);

        // verify
        assertThat(settings.getChoiceSetting(CHOICE_SETTING).getValue(), equalTo(Color.BLUE));
        settings.load();
        assertThat(settings.getChoiceSetting(CHOICE_SETTING).getValue(), equalTo(Color.BLACK));
    }

    @Test
    public void canStoreAndLoadFileSettingFromFile() throws Exception {
        // execute
        settings.save();
        settings.getFileSetting(FILE_SETTING).setValue(PROPERTIES_FILE);

        // verify
        assertThat(settings.getFileSetting(FILE_SETTING).getValue(), equalTo(PROPERTIES_FILE));
        settings.load();
        assertThat(settings.getFileSetting(FILE_SETTING).getValue(), equalTo(DEFAULT_FILE));
    }

    @Test
    public void canStoreAndLoadMultiselectSettingFromFile() throws Exception {
        // setup
        HashSet<Locale> newSet = new HashSet<Locale>(Collections.singletonList(Locale.GERMANY));

        // execute
        settings.save();
        settings.<Locale>getMultiselectSetting(MULTISELECT_SETTING).setValue(newSet);

        // verify
        assertThat(settings.getMultiselectSetting(MULTISELECT_SETTING).getValue(), equalTo(newSet));
        settings.load();
        assertThat(settings.getMultiselectSetting(MULTISELECT_SETTING).getValue(), equalTo(
                new HashSet<Locale>(Arrays.asList(Locale.GERMANY, Locale.ENGLISH)))
        );
    }

    @Test(expected = EasySettingsException.class)
    public void throwsEasySettingsExceptionIfPathDoesntExistWhileLoading() throws Exception {
        // setup
        handler = new PropertiesFileHandler(new File("/this/directory/does/not/exist.properties"));
        settings = new SettingsImpl(handler);

        // execute
        settings.load();
    }

    @Test(expected = EasySettingsException.class)
    public void throwsEasySettingsExceptionIfPathDoesntExistWhileSaving() throws Exception {
        // setup
        handler = new PropertiesFileHandler(new File("/this/directory/does/not/exist.properties"));
        settings = new SettingsImpl(handler);

        // execute
        settings.save();
    }

    private void fillSettings(Settings settings) {
        UnboundedSettingBuilder<String> usBuilder = new UnboundedSettingBuilder<>();
        UnboundedSetting<String> setting1 = usBuilder
                .forType(String.class)
                .defaultValue("Default Value")
                .withHelpMessage(HELP_MESSAGE)
                .build();
        settings.addUnboundedSetting(UNBOUNDED_SETTING, setting1);

        RangeSettingBuilder<Double> rsBuilder = new RangeSettingBuilder<>();
        RangeSetting<Double> setting2 = rsBuilder
                .forType(Double.class)
                .defaultValue(1.0)
                .lowerBound(0.0)
                .upperBound(2.0)
                .withHelpMessage(HELP_MESSAGE)
                .build();
        settings.addRangeSetting(RANGE_SETTING, setting2);

        ChoiceSettingBuilder<Color> csBuilder = new ChoiceSettingBuilder<>();
        ChoiceSetting<Color> setting3 = csBuilder
                .forType(Color.class)
                .defaultValue(Color.BLACK)
                .addChoices(Color.RED, Color.GREEN, Color.BLUE)
                .withHelpMessage(HELP_MESSAGE)
                .build();
        settings.addChoiceSetting(CHOICE_SETTING, setting3);

        FileSettingBuilder fsBuilder = new FileSettingBuilder();
        FileSetting setting4 = fsBuilder
                .defaultValue(DEFAULT_FILE)
                .withHelpMessage(HELP_MESSAGE)
                .build();
        settings.addFileSetting(FILE_SETTING, setting4);

        MultiselectSettingBuilder<Locale> msBuilder = new MultiselectSettingBuilder<>();
        MultiselectSetting<Locale> setting5 = msBuilder
                .forType((Class<HashSet<Locale>>) new HashSet<Locale>().getClass())
                .defaultValue(Locale.GERMANY, Locale.ENGLISH)
                .addChoices(Locale.CANADA, Locale.CHINA)
                .withHelpMessage(HELP_MESSAGE)
                .build();
        settings.addMultiselectSetting(MULTISELECT_SETTING, setting5);
    }
}