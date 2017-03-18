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
public final class ChoiceBoxMatcher {
    @NotNull
    @Factory
    public static Matcher<Node> hasItems(final int amount) {
        final String descriptionText = "has " + amount + " items";
        return typeSafeMatcher(ChoiceBox.class, descriptionText, node -> hasItems(node, amount));
    }

    @NotNull
    @Factory
    public static <T> Matcher<Node> hasSelectedItem(final T selection) {
        final String descriptionText = "has selection " + selection;
        return typeSafeMatcher(ChoiceBox.class, descriptionText, node -> hasSelectedItem(node, selection));
    }

    private static boolean hasItems(final ChoiceBox<?> choiceBox, final int amount) {
        return choiceBox.getItems().size() == amount;
    }

    private static <T> boolean hasSelectedItem(final ChoiceBox<?> choiceBox, final T selection) {
        return selection.equals(choiceBox.valueProperty().get());
    }
}