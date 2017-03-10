package de.torqdev.easysettings.core;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class RangeSettingTest {
    private RangeSetting<Double> rangeSetting;

    @Before
    public void setUp() throws Exception {
        rangeSetting = new RangeSetting<>(1.0, Double.class, 2.0, 4.0, "helpMessage");
    }

    @Test
    public void capsToLowerBound() throws Exception {
        assertThat(rangeSetting.getSetting().getValue(), equalTo(2.0));
    }

    @Test
    public void upperAndLowerBoundsCanBeFetched() throws Exception {
        // execute + verify
        assertThat(rangeSetting.getMin(), equalTo(2.0));
        assertThat(rangeSetting.getMax(), equalTo(4.0));
    }
}