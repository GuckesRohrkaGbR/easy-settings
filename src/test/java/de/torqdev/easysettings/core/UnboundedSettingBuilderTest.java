package de.torqdev.easysettings.core;

import org.junit.Test;

import java.util.Locale;

import static de.torqdev.easysettings.core.SettingType.UNBOUNDED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class UnboundedSettingBuilderTest {
    private static final String HELP_MESSAGE = "The locale";

    @Test
    public void createsUnboundedSettingWithAllGivenValues() throws Exception {
        UnboundedSettingBuilder<Locale> settingBuilder = new UnboundedSettingBuilder<>();

        Setting<Locale> setting = settingBuilder
                .forType(Locale.class)
                .defaultValue(Locale.GERMANY)
                .withHelpMessage(HELP_MESSAGE).build();

        assertThat(setting.getSettingType(), equalTo(UNBOUNDED));
        assertThat(setting.getValue(), equalTo(Locale.GERMANY));
        assertThat(setting.getValueType(), equalTo(Locale.class));
        assertThat(setting.getHelpMessage(), equalTo(HELP_MESSAGE));
    }
}