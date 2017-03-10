package de.torqdev.easysettings.core.converters;

import javafx.scene.paint.Color;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
@RunWith(Parameterized.class)
public class ColorStringConverterTest {
    @Parameters
    public static Iterable<? extends Object> data() {
        Field[] fields = Color.class.getDeclaredFields();
        return java.util.Arrays.stream(fields)
                .filter((mod) -> Modifier.isStatic(mod.getModifiers()) && Modifier.isPublic(mod.getModifiers()))
                .map(field -> {
                    Color myReturn = null;
                    try {
                        myReturn = (Color) field.get(Color.BLACK);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return myReturn;
                })
                .collect(Collectors.toList());
    }

    private final Color testColor;
    private final ColorStringConverter converter = new ColorStringConverter();


    public ColorStringConverterTest(Color color) {
        testColor = color;
    }

    @Test
    public void translatingColorToStringAndBackWorks() throws Exception {
        Color color = Color.BURLYWOOD;
        String colorString = converter.toString(color);
        Color stringColor = converter.fromString(colorString);

        assertThat("Error converting color: " + color, stringColor, equalTo(color));
    }

    @Test
    public void translatingStringToColorAndBackWorks() throws Exception {
        String string = converter.toString(testColor);
        Color stringColor = converter.fromString(string);
        String colorString = converter.toString(stringColor);

        assertThat("Error converting string: " + string, colorString, equalTo(string));
    }
}