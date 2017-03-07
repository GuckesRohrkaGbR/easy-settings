package de.torqdev.easysettings.matchers;

import javafx.scene.Node;
import javafx.scene.control.Button;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import static org.testfx.matcher.base.GeneralMatchers.typeSafeMatcher;

/**
 * Right now we need this matcher, because
 *
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class ButtonMatcher {
    @Factory
    public static Matcher<Node> cancelButton() {
        return typeSafeMatcher(Button.class, "is cancel button", Button::isCancelButton);
    }

    @Factory
    public static Matcher<Node> isLabeled(String label) {
        return typeSafeMatcher(Button.class, "is labeled " + label,
                               button -> label.equals(button.getText()));
    }

    @Factory
    public static Matcher<Node> defaultButton() {
        return typeSafeMatcher(Button.class, "is default button", Button::isDefaultButton);
    }
}
