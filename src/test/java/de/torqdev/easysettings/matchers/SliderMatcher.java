package de.torqdev.easysettings.matchers;

import javafx.scene.Node;
import javafx.scene.control.Slider;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

import static org.testfx.matcher.base.GeneralMatchers.typeSafeMatcher;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public final class SliderMatcher {
    @Factory
    public static Matcher<Node> fromTo(Double min, Double max) {
        String descriptionText = "goes from " + min + " to " + max;
        return typeSafeMatcher(Slider.class, descriptionText,
                               node -> min.equals(node.getMin())
                                       && max.equals(node.getMax())
        );
    }

    @Factory
    public static Matcher<Node> hasSelectedValue(Double value) {
        String descriptionText = "has selected value " + value;
        return typeSafeMatcher(Slider.class, descriptionText,
                               node -> value.equals(node.getValue())
        );
    }
}
