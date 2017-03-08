package de.torqdev.easysettings.gui.javafx;

import de.torqdev.easysettings.core.Setting;
import de.torqdev.easysettings.core.SettingBuilder;
import de.torqdev.easysettings.core.Settings;
import de.torqdev.easysettings.matchers.ChoiceBoxMatcher;
import de.torqdev.easysettings.matchers.SliderMatcher;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.Contract;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.io.File;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static de.torqdev.easysettings.matchers.ButtonMatcher.cancelButton;
import static de.torqdev.easysettings.matchers.ButtonMatcher.isLabeled;
import static de.torqdev.easysettings.matchers.SliderMatcher.fromTo;
import static de.torqdev.easysettings.matchers.SliderMatcher.hasSelectedValue;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isNotNull;
import static org.testfx.matcher.base.NodeMatchers.isNull;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

/**
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
@Ignore
public class ConfigurationDialogTest extends ApplicationTest {
    private static final ResourceBundle STR = ResourceBundle.getBundle("i18n.EasySettings", Locale.getDefault());
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.getDefault());

    private static final String STRING_SETTING = "StringSetting";
    private static final String RANGE_SETTING = "RangeSetting";
    private static final String CHOICE_SETTING = "ChoiceSetting";
    private static final String FILE_SETTING = "FileSetting";

    private final SettingBuilder builder = new SettingBuilder();

    @Override
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        stage.setTitle("ConfigurationDialogTest");
        stage.setOnCloseRequest(e -> {
        });

        StackPane root = new StackPane();
        Button openDialogButton = new Button("Open Dialog");
        openDialogButton.setId("testButton");
        openDialogButton.setOnAction(event -> {
            Optional<Settings> newSettings = testObject.showAndWait();
            newSettings.ifPresent(settings -> resultSettings = settings);
        });
        Button closeApplicationButton = new Button("Close");
        closeApplicationButton.setId("closeButton");
        closeApplicationButton.setOnAction(event -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        HBox hBox = new HBox();
        hBox.getChildren().addAll(openDialogButton, closeApplicationButton);
        root.getChildren().add(hBox);

        Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    private ConfigurationDialog testObject;
    private Settings resultSettings;

    @Before
    public void setUp() throws Exception {
        resultSettings = null;

        final Settings settings = new Settings();
        settings.addSetting(STRING_SETTING, builder
                .<String>unboundedSetting()
                .forType(String.class)
                .defaultValue("StringSample")
                .build());
        settings.addSetting(RANGE_SETTING, builder
                .<Double>rangeSetting()
                .forType(Double.class)
                .defaultValue(1.0)
                .build());
        settings.addSetting(CHOICE_SETTING, builder
                .<String>choiceSetting()
                .forType(String.class)
                .defaultValue("Sel 3")
                .build());
        settings.addSetting(FILE_SETTING, builder
                .fileSetting()
                .defaultValue(new File("/file/path"))
                .build());

        Platform.runLater(() -> testObject = new ConfigurationDialog(settings));
        clickOn("#testButton");
    }

    @After
    public void tearDown() {
        if (lookup(cancelButton()).tryQuery().isPresent()) {
            clickOn(cancelButton());
        }
        clickOn("#closeButton");
    }

    @Test
    public void settingsArePresentedInTheRightOrder() throws Exception {
        LinkedList<String> labelTexts = getLabelTextsInOrder();

        assertEquals(4, labelTexts.size());
        assertThat(labelTexts.get(0), containsString(STRING_SETTING));
        assertThat(labelTexts.get(1), containsString(RANGE_SETTING));
        assertThat(labelTexts.get(2), containsString(CHOICE_SETTING));
        assertThat(labelTexts.get(3), containsString(FILE_SETTING));
    }

    @Contract("-> !null")
    private LinkedList<String> getLabelTextsInOrder() {
        LinkedList<String> myReturn = new LinkedList<>();

        final int[] idx = {0};
        testObject.getDialogPane().getChildren().forEach(node -> ((Parent) node).getChildrenUnmodifiable().forEach(node1 -> {
            if (node1 instanceof Label) {
                myReturn.add(idx[0], ((Label) node1).getText());
                idx[0]++;
            }
        }));

        return myReturn;
    }

    @Test
    public void cancelDoesntChangeSettings() throws Exception {
        clickOn(cancelButton());
        assertNull(resultSettings);
    }

    @Test
    public void stringSettingIsDisplayedAsATextField() throws Exception {
        verifyThat("StringSample",
                TextInputControlMatchers.hasText(containsString("StringSample")));
    }

    @Test
    public void rangeSettingIsDisplayedAsASlider() throws Exception {
        verifyThat(lookup(fromTo(0.0, 2.0)), isVisible());
        verifyThat(lookup(hasSelectedValue(1.0)), isVisible());
    }

    @Test
    public void choiceSettingIsDisplayedAsAChoiceBox() throws Exception {
        verifyThat(lookup(ChoiceBoxMatcher.hasSelectedItem("Sel 3")), isVisible());
        verifyThat(lookup(ChoiceBoxMatcher.hasItems(3)), isVisible());
    }

    @Test
    public void choiceSettingDisplaysTheConfiguredChoice() throws Exception {
        verifyThat("Sel 1", isNull());
        verifyThat("Sel 2", isNull());
        verifyThat("Sel 3", isVisible());
    }

    @Test
    public void fileChooserIsDisplayedCorrectly() throws Exception {
        verifyThat(STR.getString("open.file"), isNotNull());
        verifyThat(STR.getString("open.file"), isVisible());
    }

    @Test
    public void savingStringOptionWorks() throws Exception {
        String testString = "Test";
        doubleClickOn(TextInputControlMatchers.hasText("StringSample"));
        write(testString);
        clickOn(isLabeled(STR.getString("save")));

        checkResultSettingsValidity();
        assertThat(resultSettings.getSettings().get(STRING_SETTING).getValue().toString(),
                containsString(testString));
    }

    @Test
    public void savingRangeOptionWorks() throws Exception {
        Optional<Slider> slider = lookup(fromTo(0.0, 2.0)).tryQuery();

        assertTrue(slider.isPresent());

        if (slider.isPresent()) {
            Slider testSlider = slider.get();
            CyclicBarrier barrier = new CyclicBarrier(2);
            Platform.runLater(() -> {
                testSlider.adjustValue(2.0);
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            barrier.await();
        }

        clickOn(isLabeled(STR.getString("save")));

        checkResultSettingsValidity();
        assertThat(resultSettings.getSettings().get(RANGE_SETTING).getValue().toString(),
                containsString("2.0"));
    }

    @Test
    public void savingChoiceOptionWorks() throws Exception {
        clickOn(ChoiceBoxMatcher.hasSelectedItem("Sel 3"));
        clickOn("Sel 2");

        clickOn(isLabeled(STR.getString("save")));

        checkResultSettingsValidity();
        assertThat(resultSettings.getSettings().get(CHOICE_SETTING).getValue().toString(),
                containsString("Sel 2"));
    }

    private void checkResultSettingsValidity() {
        assertThat(resultSettings, notNullValue());
        assertThat(resultSettings.getSettings(), notNullValue());
    }

    @Test
    public void savingFileOptionWorks() throws Exception {
        Optional<Label> label = lookup((new File("/file/path").getPath())).tryQuery();
        assertTrue(label.isPresent());

        if (label.isPresent()) {
            CyclicBarrier barrier = new CyclicBarrier(2);
            Platform.runLater(() -> {
                label.get().textProperty().setValue("/new/file");
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            barrier.await();
        }

        clickOn(isLabeled(STR.getString("save")));

        checkResultSettingsValidity();
        assertThat(resultSettings.getSettings().get(FILE_SETTING).getValue().toString(), containsString((new File("/new/file")).getPath()));
    }

    @Test
    public void rangeOptionShowsMinMaxAndCurrentValue() throws Exception {
        Optional<Slider> slider = lookup(SliderMatcher.fromTo(0.0, 2.0)).tryQuery();

        assert slider.isPresent();
        Slider testSlider = slider.get();
        assertTrue(testSlider.isShowTickLabels());

        verifyThat("1", isNotNull());
        verifyThat("1", isVisible());

        // Synchronize FX Application thread for value changing
        CyclicBarrier barrier = new CyclicBarrier(2);
        Platform.runLater(() -> {
            testSlider.valueProperty().setValue(1.2);
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        barrier.await();

        verifyThat(NUMBER_FORMAT.format(1.2), isNotNull());
        verifyThat(NUMBER_FORMAT.format(1.2), isVisible());
    }
}
