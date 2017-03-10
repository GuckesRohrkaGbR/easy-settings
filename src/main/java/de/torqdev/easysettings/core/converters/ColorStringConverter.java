package de.torqdev.easysettings.core.converters;

import javafx.scene.paint.Color;
import javafx.util.StringConverter;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class ColorStringConverter extends StringConverter<Color> {
    @Override
    public String toString(Color color) {
        return (color == null) ? "" : String.format("#%02X%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255),
                (int) (color.getOpacity() * 255));
    }

    @Override
    public Color fromString(String stringColor) {
        return (stringColor == null) ? null : Color.web(stringColor);
    }
}
