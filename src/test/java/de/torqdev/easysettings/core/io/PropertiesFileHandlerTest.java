package de.torqdev.easysettings.core.io;

import de.torqdev.easysettings.core.*;
import de.torqdev.easysettings.core.converters.SetStringConverter;
import de.torqdev.easysettings.core.converters.StringConverterUtil;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static de.torqdev.easysettings.core.SettingsTestUtil.*;
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
                    .getResource("properties/settings-test.properties")
                    .getFile()
    );

    private Settings settings;
    private PropertiesHandler handler;

    @BeforeClass
    public static void registerSetStringConverter() {
        StringConverterUtil.registerStringConverter(Set.class, new SetStringConverter<>(Locale.class));
        StringConverterUtil.registerStringConverter(HashSet.class, new SetStringConverter<>(Locale.class));
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
        final Set<Locale> oldSet = new HashSet<>(Arrays.asList(Locale.GERMANY, Locale.ENGLISH));
        final Set<Locale> newSet = new HashSet<>(Collections.singletonList(Locale.GERMANY));

        // execute
        settings.save();
        settings.<Locale>getMultiselectSetting(MULTISELECT_SETTING).setValue(newSet);

        // verify
        assertThat(settings.getMultiselectSetting(MULTISELECT_SETTING).getValue(), equalTo(newSet));
        settings.load();
        assertThat(settings.getMultiselectSetting(MULTISELECT_SETTING).getValue(), equalTo(oldSet));
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
}