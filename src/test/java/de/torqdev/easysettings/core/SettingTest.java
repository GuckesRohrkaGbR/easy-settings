package de.torqdev.easysettings.core;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Arrays;

import static de.torqdev.easysettings.core.SettingType.CHOICE;
import static de.torqdev.easysettings.core.SettingType.RANGE;
import static de.torqdev.easysettings.core.SettingType.UNBOUNDED;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class SettingTest {
    public static final String DEFAULT_STRING_VALUE = "DefaultValue";

    private Setting<String> testStringSetting;
    private Setting<Double> testRangeSetting;
    private Setting<Color> testChoiceSetting;

    @Before
    public void setUp() throws Exception {
        testStringSetting = new Setting<>(DEFAULT_STRING_VALUE, String.class);
        testRangeSetting = new Setting<>(0.0, Double.class, -1.0, 1.0);
        testChoiceSetting = new Setting<>(Color.BLACK, Color.class, Arrays.asList(Color.BLACK, Color.RED,
                Color.GREEN, Color.BLUE));
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
        assertNotNull(testRangeSetting.getMinValue());
        assertNotNull(testRangeSetting.getMaxValue());
    }

    @Test
    public void settingCanBeChanged() throws Exception {
        testStringSetting.setValue("New Value");
        assertThat(testStringSetting.getValue(), equalTo("New Value"));
    }

    @Test
    public void choiceSettingsReturnValidChoices() throws Exception {
        assertThat(testChoiceSetting.getChoices(), hasItems(Color.BLUE, Color.RED, Color.GREEN, Color.BLACK));
    }

    @Test
    public void settingReturnsHelpMessage() throws Exception {
        testStringSetting = new Setting<>(DEFAULT_STRING_VALUE, String.class, "Simple String Setting");
        assertThat(testStringSetting.getHelpMessage(), equalTo("Simple String Setting"));
    }
}