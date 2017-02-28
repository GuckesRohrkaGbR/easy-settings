package de.torqdev.easysettings.matchers;

import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.jetbrains.annotations.NotNull;

import static org.testfx.matcher.base.GeneralMatchers.typeSafeMatcher;

/**
 * @author <a href="mailto:christopher.guckes@torq-dev.de">Christopher Guckes</a>
 * @version 1.0
 */
public class ChoiceBoxMatcher {
    @NotNull
    @Factory
    public static Matcher<Node> hasItems(int amount) {
        String descriptionText = "has " + amount + " items";
        return typeSafeMatcher(ChoiceBox.class, descriptionText, node -> hasItems(node, amount));
    }

    @NotNull
    @Factory
    public static <T> Matcher<Node> hasSelectedItem(T selection) {
        String descriptionText = "has selection " + selection;
        return typeSafeMatcher(ChoiceBox.class, descriptionText, node -> hasSelectedItem(node, selection));
    }

    private static boolean hasItems(ChoiceBox<?> choiceBox, int amount) {
        return choiceBox.getItems().size() == amount;
    }

    private static <T> boolean hasSelectedItem(ChoiceBox<?> choiceBox, T selection) {
        return selection.equals(choiceBox.valueProperty().get());
    }
}
