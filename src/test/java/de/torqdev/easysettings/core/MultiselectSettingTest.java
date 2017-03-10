package de.torqdev.easysettings.core;


import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class MultiselectSettingTest {
    private static final HashSet<Locale> CHOSEN = new HashSet<>(Arrays.asList(Locale.CANADA, Locale.CANADA_FRENCH));
    private static final HashSet<Locale> NOT_CHOSEN = new HashSet<>(Arrays.asList(Locale.CHINA, Locale.FRANCE, Locale.GERMAN));
    private static final HashSet<Locale> ALL_CHOICES = new HashSet<>();

    @BeforeClass
    public static void setUp() throws Exception {
        ALL_CHOICES.addAll(CHOSEN);
        ALL_CHOICES.addAll(NOT_CHOSEN);
    }

    @Test
    public void choicesContainInitialSettingsAndAllOtherChoices() throws Exception {
        // setup
        MultiselectSetting<Locale> setting = new MultiselectSetting<Locale>(CHOSEN, (Class<HashSet<Locale>>) new HashSet<Locale>().getClass(), NOT_CHOSEN, null);

        // execute
        Set<Locale> choices = setting.getChoices();

        // verify
        assertThat(choices, equalTo(ALL_CHOICES));
    }
}