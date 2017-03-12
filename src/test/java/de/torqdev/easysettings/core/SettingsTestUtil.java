package de.torqdev.easysettings.core;

import javafx.scene.paint.Color;

import java.io.File;
import java.util.Locale;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public final class SettingsTestUtil {
    public static final String UNBOUNDED_SETTING = "Unbounded Setting";
    public static final String HELP_MESSAGE = "Help Message";
    public static final String DEFAULT_VALUE = "Default Value";
    public static final String CHOICE_SETTING = "Choice Setting";
    public static final String RANGE_SETTING = "Range Setting";
    public static final String MULTISELECT_SETTING = "Multiselect Setting";
    public static final String FILE_SETTING = "File Setting";
    public static final File DEFAULT_FILE = new File(System.getProperty("java.io.tmpdir") + "file");

    public static void fillSettings(final Settings settings) {
        final UnboundedSettingBuilder<String> usBuilder = new UnboundedSettingBuilder<>();
        final UnboundedSetting<String> setting1 = usBuilder
                .forType(String.class)
                .defaultValue("Default Value")
                .withHelpMessage(HELP_MESSAGE)
                .build();

        final RangeSettingBuilder<Double> rsBuilder = new RangeSettingBuilder<>();
        final RangeSetting<Double> setting2 = rsBuilder
                .forType(Double.class)
                .defaultValue(1.0)
                .lowerBound(0.0)
                .upperBound(2.0)
                .withHelpMessage(HELP_MESSAGE)
                .build();

        final ChoiceSettingBuilder<Color> csBuilder = new ChoiceSettingBuilder<>();
        final ChoiceSetting<Color> setting3 = csBuilder
                .forType(Color.class)
                .defaultValue(Color.BLACK)
                .addChoices(Color.RED, Color.GREEN, Color.BLUE)
                .withHelpMessage(HELP_MESSAGE)
                .build();

        final FileSettingBuilder fsBuilder = new FileSettingBuilder();
        final FileSetting setting4 = fsBuilder
                .defaultValue(DEFAULT_FILE)
                .withHelpMessage(HELP_MESSAGE)
                .build();

        final MultiselectSettingBuilder<Locale> msBuilder = new MultiselectSettingBuilder<>();
        final MultiselectSetting<Locale> setting5 = msBuilder
                .defaultValue(Locale.GERMANY, Locale.ENGLISH)
                .addChoices(Locale.CANADA, Locale.CHINA)
                .withHelpMessage(HELP_MESSAGE)
                .build();

        settings.addUnboundedSetting(UNBOUNDED_SETTING, setting1);
        settings.addRangeSetting(RANGE_SETTING, setting2);
        settings.addChoiceSetting(CHOICE_SETTING, setting3);
        settings.addFileSetting(FILE_SETTING, setting4);
        settings.addMultiselectSetting(MULTISELECT_SETTING, setting5);
    }

    private SettingsTestUtil() {
        // private utility class constructor
    }
}
