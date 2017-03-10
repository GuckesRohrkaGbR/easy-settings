package de.torqdev.easysettings.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public abstract class SettingsTest {
    private static final String KEY_DOESNT_EXIST = "doesnt-exist";

    private Settings testObject;

    @Before
    public void setUp() throws Exception {
        testObject = getNewTestObject();
    }

    protected abstract Settings getNewTestObject();

    @Test
    public void requestingNonExistingKeyDoesntCrash() throws Exception {
        assertNull(testObject.getSetting(KEY_DOESNT_EXIST));
    }

    @Test(expected = EasySettingsException.class)
    public void loadingEmptySettingsThrowsAnException() throws Exception {
        testObject.load();
    }
}