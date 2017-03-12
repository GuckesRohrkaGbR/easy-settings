package de.torqdev.easysettings.ui.javafx;

import de.torqdev.easysettings.core.Settings;
import de.torqdev.easysettings.core.SettingsFactory;
import de.torqdev.easysettings.core.converters.SetStringConverter;
import de.torqdev.easysettings.core.converters.StringConverterUtil;
import de.torqdev.easysettings.core.io.PropertiesFileHandlerTest;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.jetbrains.annotations.Contract;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TextInputControlMatchers;

import java.io.File;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static de.torqdev.easysettings.core.SettingsTestUtil.*;
import static de.torqdev.easysettings.matchers.ButtonMatcher.cancelButton;
import static de.torqdev.easysettings.matchers.ButtonMatcher.isLabeled;
import static de.torqdev.easysettings.matchers.SliderMatcher.fromTo;
import static de.torqdev.easysettings.matchers.SliderMatcher.hasSelectedValue;
import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;

/**
 * @author Christopher Guckes (christopher.guckes@torq-dev.de)
 * @version 1.0
 */
public class ConfigurationDialogTest extends ApplicationTest {
    private static final ResourceBundle STR = ResourceBundle.getBundle("i18n.EasySettings", Locale.getDefault());
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.getDefault());
    private static final File PROPERTIES_FILE = new File(
            PropertiesFileHandlerTest.class
                    .getClassLoader()
                    .getResource("properties/settings-test.properties")
                    .getFile()
    );

    @Override
    public void init() throws Exception {
        FxToolkit.registerStage(Stage::new);
    }

    @Override
    public void start(final Stage stage) throws Exception {
        stage.setTitle("ConfigurationDialogTest");
        stage.setOnCloseRequest(e -> {
        });

        final StackPane root = new StackPane();
        final Button openDialogButton = new Button("Open Dialog");
        openDialogButton.setId("testButton");
        openDialogButton.setOnAction(event -> {
            final Optional<Settings> newSettings = testObject.showAndWait();
            newSettings.ifPresent(settings -> resultSettings = settings);
        });
        final Button closeApplicationButton = new Button("Close");
        closeApplicationButton.setId("closeButton");
        closeApplicationButton.setOnAction(event -> stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST)));
        final HBox hBox = new HBox();
        hBox.getChildren().addAll(openDialogButton, closeApplicationButton);
        root.getChildren().add(hBox);

        final Scene scene = new Scene(root, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    private ConfigurationDialog testObject;
    private Settings resultSettings;

    @BeforeClass
    public static void registerSetStringConverter() {
        StringConverterUtil.registerStringConverter(Set.class, new SetStringConverter<>(Locale.class));
        StringConverterUtil.registerStringConverter(HashSet.class, new SetStringConverter<>(Locale.class));
    }

    @Before
    public void setUp() throws Exception {
        resultSettings = null;

        final Settings settings = SettingsFactory.getSettings(PROPERTIES_FILE);
        fillSettings(settings);

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
    public void cancelDoesntChangeSettings() throws Exception {
        clickOn(cancelButton());
        assertNull(resultSettings);
    }

    @Test
    public void stringSettingIsDisplayedAsATextField() throws Exception {
        verifyThat("Default Value",
                TextInputControlMatchers.hasText(containsString("Default Value")));
    }

    @Test
    public void fileChooserIsDisplayedCorrectly() throws Exception {
        verifyThat(STR.getString("open.file"), isNotNull());
        verifyThat(STR.getString("open.file"), isVisible());
    }

    @Test
    public void rangeSettingIsDisplayedAsASlider() throws Exception {
        verifyThat(lookup(fromTo(0.0, 2.0)), isVisible());
        verifyThat(lookup(hasSelectedValue(1.0)), isVisible());
    }

    @Test
    public void rangeOptionShowsMinMaxAndCurrentValue() throws Exception {
        final Optional<Slider> slider = lookup(SliderMatcher.fromTo(0.0, 2.0)).tryQuery();

        assert slider.isPresent();
        final Slider testSlider = slider.get();
        assertTrue(testSlider.isShowTickLabels());

        verifyThat("1", isNotNull());
        verifyThat("1", isVisible());

        // Synchronize FX Application thread for value changing
        final CyclicBarrier barrier = new CyclicBarrier(2);
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

    @Test
    public void choiceSettingIsDisplayedAsAChoiceBox() throws Exception {
        verifyThat(lookup(ChoiceBoxMatcher.hasSelectedItem(Color.BLACK)), isVisible());
        verifyThat(lookup(ChoiceBoxMatcher.hasItems(4)), isVisible());
    }

    @Test
    public void choiceSettingDisplaysTheConfiguredChoice() throws Exception {
        verifyThat("0xff0000ff", isNull());
        verifyThat("0x008000ff", isNull());
        verifyThat("0x0000ffff", isNull());
        verifyThat("0x000000ff", isVisible());
        clickOn("0x000000ff");
        verifyThat("0xff0000ff", isVisible());
        verifyThat("0x008000ff", isVisible());
        verifyThat("0x0000ffff", isVisible());
        verifyThat("0x000000ff", isVisible());
    }

    @Test
    public void settingsArePresentedInTheRightOrder() throws Exception {
        final LinkedList<String> labelTexts = getLabelTextsInOrder();

        assertEquals(4, labelTexts.size());
        assertThat(labelTexts.get(0), containsString(UNBOUNDED_SETTING));
        assertThat(labelTexts.get(1), containsString(RANGE_SETTING));
        assertThat(labelTexts.get(2), containsString(CHOICE_SETTING));
        assertThat(labelTexts.get(3), containsString(FILE_SETTING));
    }

    @Contract("-> !null")
    private LinkedList<String> getLabelTextsInOrder() {
        final LinkedList<String> myReturn = new LinkedList<>();

        final int[] idx = {0};
        testObject
                .getDialogPane()
                .getChildren()
                .forEach(
                        node -> ((Parent) node).getChildrenUnmodifiable().forEach(node1 -> {
                            if (node1 instanceof Label) {
                                myReturn.add(idx[0], ((Label) node1).getText());
                                idx[0]++;
                            }
                        }));

        return myReturn;
    }

    @Test
    public void savingStringOptionWorks() throws Exception {
        final String testString = "Test";
        doubleClickOn(TextInputControlMatchers.hasText("Default Value"));
        write(testString);
        clickOn(isLabeled(STR.getString("save")));

        checkResultSettingsValidity();
        assertThat(resultSettings.<String>getUnboundedSetting(UNBOUNDED_SETTING).getValue(),
                containsString(testString));

        // restore
        resultSettings.<String>getUnboundedSetting(UNBOUNDED_SETTING).setValue("Default Value");
        resultSettings.save();
    }


    @Test
    public void savingRangeOptionWorks() throws Exception {
        final Optional<Slider> slider = lookup(fromTo(0.0, 2.0)).tryQuery();

        assertTrue(slider.isPresent());

        if (slider.isPresent()) {
            final Slider testSlider = slider.get();
            final CyclicBarrier barrier = new CyclicBarrier(2);
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
        assertThat(resultSettings.<Double>getRangeSetting(RANGE_SETTING).getValue(),
                equalTo(2.0));

        // restore
        resultSettings.<Double>getRangeSetting(RANGE_SETTING).setValue(1.0);
        resultSettings.save();
    }

    @Test
    public void savingChoiceOptionWorks() throws Exception {
        clickOn(ChoiceBoxMatcher.hasSelectedItem(Color.BLACK));
        clickOn("0x0000ffff");

        clickOn(isLabeled(STR.getString("save")));

        checkResultSettingsValidity();
        assertThat(resultSettings.<Color>getChoiceSetting(CHOICE_SETTING).getValue(),
                equalTo(Color.BLUE));

        // restore
        resultSettings.<Color>getChoiceSetting(CHOICE_SETTING).setValue(Color.BLACK);
        resultSettings.save();
    }


    @Test
    public void savingFileOptionWorks() throws Exception {
        final File originalFile = new File(System.getProperty("java.io.tmpdir") + "/file");
        final Optional<Label> label = lookup(originalFile.getPath()).tryQuery();
        assertTrue(label.isPresent());

        if (label.isPresent()) {
            final CyclicBarrier barrier = new CyclicBarrier(2);
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
        assertThat(resultSettings.getFileSetting(FILE_SETTING).getValue().toString(), containsString(
                (new File("/new/file")).getPath()));

        // restore
        resultSettings.getFileSetting(FILE_SETTING).setValue(originalFile);
        resultSettings.save();
    }

    @Test
    public void multiselectSettingsAreDisplayedCorrectly() throws Exception {
        //TODO
        // setup

        // execute

        // verify

        // restore
    }

    @Test
    public void savingMultiselectSettingsWorks() throws Exception {
        //TODO
        // setup

        // execute

        // verify

        // restore
    }

    @Test
    public void tooltipsArePresentForSettingsWithHelpMessages() throws Exception {
        //TODO
        // setup

        // execute

        // verify

        // restore
    }

    private void checkResultSettingsValidity() {
        assertThat(resultSettings, notNullValue());
        assertThat(resultSettings, notNullValue());
    }
}
