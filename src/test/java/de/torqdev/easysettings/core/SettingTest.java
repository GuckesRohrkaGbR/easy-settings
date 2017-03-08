package de.torqdev.easysettings.core;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;

import static de.torqdev.easysettings.core.SettingType.CHOICE;
import static de.torqdev.easysettings.core.SettingType.RANGE;
import static de.torqdev.easysettings.core.SettingType.UNBOUNDED;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class SettingTest {
    private static final String DEFAULT_STRING_VALUE = "DefaultValue";

    private final SettingBuilder builder = new SettingBuilder();

    private Setting<String> testStringSetting;
    private Setting<Double> testRangeSetting;
    private Setting<Color> testChoiceSetting;

    @Before
    public void setUp() throws Exception {
        testStringSetting = builder
                .<String>unboundedSetting()
                .forType(String.class)
                .defaultValue(DEFAULT_STRING_VALUE)
                .build();
        testRangeSetting = builder
                .<Double>rangeSetting()
                .forType(Double.class)
                .defaultValue(0.0)
                .lowerBound(-1.0)
                .upperBound(1.0)
                .build();
        testChoiceSetting = builder
                .<Color>choiceSetting()
                .forType(Color.class)
                .defaultValue(Color.BLACK)
                .addChoices(Color.RED, Color.GREEN, Color.BLUE)
                .build();
    }

    @Test
    public void settingReturnsItsValue() throws Exception {
        assertThat(testStringSetting.getValue(), equalTo(DEFAULT_STRING_VALUE));
    }

    @Test
    public void settingReturnsItsValueType() throws Exception {
        assertThat(testStringSetting.getValueType(), equalTo(String.class));
    }

    @Test
    public void unboundedSettingReturnsCorrectType() throws Exception {
        assertThat(testStringSetting.getSettingType(), equalTo(UNBOUNDED));
    }

    @Test
    public void rangeSettingReturnsCorrectType() throws Exception {
        assertThat(testRangeSetting.getSettingType(), equalTo(RANGE));
    }

    @Test
    public void choiceSettingReturnsCorrectType() throws Exception {
        assertThat(testChoiceSetting.getSettingType(), equalTo(CHOICE));
    }

    @Test
    public void rangeSettingReturnsValidRange() throws Exception {
        RangeSetting<Double> setting = (RangeSetting<Double>) testRangeSetting;

        assertNotNull(setting.getMin());
        assertNotNull(setting.getMax());

        assertThat(setting.getMin(), equalTo(-1.0));
        assertThat(setting.getMax(), equalTo(1.0));
    }

    @Test
    public void settingCanBeChanged() throws Exception {
        testStringSetting.setValue("New Value");
        assertThat(testStringSetting.getValue(), equalTo("New Value"));
    }

    @Test
    public void choiceSettingsReturnValidChoices() throws Exception {
        System.out.println(((ChoiceSetting<Color>) testChoiceSetting).getChoices());
        assertThat(
                ((ChoiceSetting<Color>) testChoiceSetting).getChoices(),
                containsInAnyOrder(Color.BLUE, Color.RED, Color.GREEN, Color.BLACK)
        );
    }

    @Test
    public void settingReturnsHelpMessage() throws Exception {
        String helpMessage = "Simple String Setting";

        testStringSetting = builder
                .<String>unboundedSetting()
                .forType(String.class)
                .defaultValue(DEFAULT_STRING_VALUE)
                .withHelpMessage(helpMessage)
                .build();

        assertThat(testStringSetting.getHelpMessage(), equalTo(helpMessage));
    }
}