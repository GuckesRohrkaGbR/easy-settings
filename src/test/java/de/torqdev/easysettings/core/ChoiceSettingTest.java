package de.torqdev.easysettings.core;

import javafx.scene.paint.Color;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class ChoiceSettingTest {
    @Test
    public void choicesContainDefaultValue() throws Exception {
        // setup
        final ChoiceSetting<Color> setting = new ChoiceSetting<>(Color.BLACK, Color.class,
                new HashSet<>(Arrays.asList(Color.BLUE, Color.RED, Color.GREEN)), null);

        // execute
        final Set<Color> choices = setting.getChoices();

        // verify
        assertThat(choices, containsInAnyOrder(Color.BLACK, Color.BLUE, Color.RED, Color.GREEN));
    }
}