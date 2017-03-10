package de.torqdev.easysettings.core.io;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertTrue;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class PropertiesHandlerFactoryTest {
    @Test
    public void coverPrivateConstructor() throws Exception {
        Constructor<?> constructor  = PropertiesHandlerFactory.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}