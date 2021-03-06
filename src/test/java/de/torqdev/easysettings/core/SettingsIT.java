package de.torqdev.easysettings.core;

import de.torqdev.easysettings.core.converters.SetStringConverter;
import de.torqdev.easysettings.core.converters.StringConverterUtil;
import de.torqdev.easysettings.core.io.PropertiesHandler;
import de.torqdev.easysettings.core.io.PropertiesFileHandler;
import de.torqdev.easysettings.core.io.PropertiesHandlerFactory;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static de.torqdev.easysettings.core.SettingType.*;
import static de.torqdev.easysettings.core.SettingsTestUtil.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
@SuppressWarnings("ConstantConditions")
public class SettingsIT {
    private static final File PROPERTIES_FILE = new File(
            SettingsIT.class
                    .getClassLoader()
                    .getResource("properties/integration-test.properties")
                    .getFile()
    );
    private static final PropertiesHandler HANDLER = PropertiesHandlerFactory.getHandlerFor(PROPERTIES_FILE);

    private Settings testObject;

    @BeforeClass
    public static void registerHashMapStringConverter() {
        StringConverterUtil.registerStringConverter(Set.class, new SetStringConverter<>(Locale.class));
        StringConverterUtil.registerStringConverter(HashSet.class, new SetStringConverter<>(Locale.class));
    }

    @Before
    public void setUp() throws Exception {
        testObject = new SettingsImpl(HANDLER);
    }

    @Test
    public void canCreateUnboundedSetting() throws Exception {
        // setup
        final UnboundedSettingBuilder<String> usBuilder = new UnboundedSettingBuilder<>();
        final UnboundedSetting<String> setting = usBuilder
                .forType(String.class)
                .defaultValue("Default Value")
                .withHelpMessage(HELP_MESSAGE)
                .build();

        // execute
        testObject.addUnboundedSetting(UNBOUNDED_SETTING, setting);

        // verify
        assertThat(testObject.getUnboundedSetting(UNBOUNDED_SETTING).getValue(), equalTo(DEFAULT_VALUE));
    }

    @Test
    public void canCreateChoiceSetting() throws Exception {
        // setup
        final ChoiceSettingBuilder<Color> csBuilder = new ChoiceSettingBuilder<>();
        final ChoiceSetting<Color> setting = csBuilder
                .forType(Color.class)
                .defaultValue(Color.BLACK)
                .addChoices(Color.RED, Color.GREEN, Color.BLUE)
                .build();

        // execute
        testObject.addChoiceSetting(CHOICE_SETTING, setting);

        // verify
        assertThat(testObject.getChoiceSetting(CHOICE_SETTING).getValue(), equalTo(Color.BLACK));
    }

    @Test
    public void canCreateRangeSetting() throws Exception {
        // setup
        final RangeSettingBuilder<Double> rsBuilder = new RangeSettingBuilder<>();
        final RangeSetting<Double> setting = rsBuilder
                .forType(Double.class)
                .defaultValue(1.0)
                .lowerBound(0.0)
                .upperBound(2.0)
                .withHelpMessage(HELP_MESSAGE)
                .build();

        // execute
        testObject.addRangeSetting(RANGE_SETTING, setting);

        // verify
        assertThat(testObject.getRangeSetting(RANGE_SETTING).getValue(), equalTo(1.0));
    }

    @Test
    public void canCreateMultiselectSetting() throws Exception {
        // setup
        final MultiselectSettingBuilder<Locale> msBuilder = new MultiselectSettingBuilder<>();
        final MultiselectSetting<Locale> setting = msBuilder
                .defaultValue(Locale.GERMANY, Locale.ENGLISH)
                .addChoices(Locale.CANADA, Locale.CHINA)
                .withHelpMessage(HELP_MESSAGE)
                .build();

        // execute
        testObject.addMultiselectSetting(MULTISELECT_SETTING, setting);

        // verify
        assertThat(testObject.getMultiselectSetting(MULTISELECT_SETTING).getValue(), containsInAnyOrder(Locale.GERMANY, Locale.ENGLISH));
    }

    @Test
    public void canCreateFileSetting() throws Exception {
        // setup
        final FileSettingBuilder fsBuilder = new FileSettingBuilder();
        final FileSetting setting = fsBuilder
                .defaultValue(DEFAULT_FILE)
                .withHelpMessage(HELP_MESSAGE)
                .build();

        // execute
        testObject.addFileSetting(FILE_SETTING, setting);
        
        // verify
        assertThat(testObject.getFileSetting(FILE_SETTING).getValue(), equalTo(DEFAULT_FILE));
    }

    @Test
    public void canFetchKeyToTypeMapping() throws Exception {
        // setup
        fillSettings(testObject);

        // execute
        final Map<String, SettingType> settingsWithType = testObject.getSettingTypes();

        // verify
        assertThat(settingsWithType, hasEntry(UNBOUNDED_SETTING, UNBOUNDED));
        assertThat(settingsWithType, hasEntry(RANGE_SETTING, RANGE));
        assertThat(settingsWithType, hasEntry(CHOICE_SETTING, CHOICE));
        assertThat(settingsWithType, hasEntry(FILE_SETTING, FILE));
        assertThat(settingsWithType, hasEntry(MULTISELECT_SETTING, MULTISELECT));
    }

    @Test
    public void listOfAllSettingsWithTypeHasCorrectOrder() throws Exception {
        // setup
        fillSettings(testObject);

        // execute
        final Map<String, SettingType> settingsWithType = testObject.getSettingTypes();

        // verify
        assertThat(settingsWithType.keySet(), contains(UNBOUNDED_SETTING, RANGE_SETTING, CHOICE_SETTING, FILE_SETTING, MULTISELECT_SETTING));
    }

    @Test
    public void canSaveSettingsToString() throws Exception {
        // setup
        fillSettings(testObject);

        // execute
        testObject.save();

        // verify
        final String content = new String(Files.readAllBytes(PROPERTIES_FILE.toPath()));
        assertThat(content, containsString(DEFAULT_VALUE));
    }

    @Test
    public void returnsGenericSettingForExistingKey() throws Exception {
        // setup
        fillSettings(testObject);

        // execute
        final Setting<String> unbounded = testObject.getSetting(UNBOUNDED_SETTING);
        final Setting<Double> range = testObject.getSetting(RANGE_SETTING);
        final Setting<Color> choice = testObject.getSetting(CHOICE_SETTING);
        final Setting<File> file = testObject.getSetting(FILE_SETTING);
        final Setting<Set<Locale>> multiselect = testObject.getSetting(MULTISELECT_SETTING);

        // verify
        assertThat(unbounded.getValue(), equalTo(DEFAULT_VALUE));
        assertThat(range.getValue(), equalTo(1.0));
        assertThat(choice.getValue(), equalTo(Color.BLACK));
        assertThat(file.getValue(), equalTo(DEFAULT_FILE));
        assertThat(multiselect.getValue(), containsInAnyOrder(Locale.GERMANY, Locale.ENGLISH));
    }

    @Test
    public void storedSettingsGetLoadedCorrectly() throws Exception {
        // setup
        fillSettings(testObject);

        testObject.getSetting(UNBOUNDED_SETTING).setValue("New String");
        testObject.save();

        // execute
        final Settings newSettings = new SettingsImpl(new PropertiesFileHandler(PROPERTIES_FILE));
        fillSettings(newSettings);
        newSettings.load();

        final Setting<String> unbounded = newSettings.getSetting(UNBOUNDED_SETTING);
        final Setting<Double> range = newSettings.getSetting(RANGE_SETTING);
        final Setting<Color> choice = newSettings.getSetting(CHOICE_SETTING);
        final Setting<File> file = newSettings.getSetting(FILE_SETTING);
        final Setting<Set<Locale>> multiselect = newSettings.getSetting(MULTISELECT_SETTING);

        // verify
        assertThat(unbounded.getValue(), equalTo("New String"));
        assertThat(range.getValue(), equalTo(1.0));
        assertThat(choice.getValue(), equalTo(Color.BLACK));
        assertThat(file.getValue(), equalTo(DEFAULT_FILE));
        assertThat(multiselect.getValue(), containsInAnyOrder(Locale.GERMANY, Locale.ENGLISH));
    }

    @Test
    public void canFetchHelpMessages() throws Exception {
        // setup
        fillSettings(testObject);

        // execute
        final String help = testObject.getChoiceSetting(CHOICE_SETTING).getSetting().getHelpMessage();

        // verify
        assertThat(help, equalTo(HELP_MESSAGE));
    }
}