package de.torqdev.easysettings.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class RangeSettingTest {
    @Test
    public void capsToLowerBound() throws Exception {
        RangeSetting<Double> rangeSetting = new RangeSetting<>(1.0, Double.class, 2.0, 4.0, "helpMessage");
        assertThat(rangeSetting.getSetting().getValue(), equalTo(2.0));
    }
}